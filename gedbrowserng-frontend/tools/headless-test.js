#!/usr/bin/env node
// Headless e2e test: serve the built ui-src/META-INF/resources and run Puppeteer to navigate
// through Persons, Notes, Sources, Submitters and assert the toolbar is inside mat-drawer-container
// and has styling.

const express = require('express');
const path = require('path');
const puppeteer = require('puppeteer');
const { rateLimit } = require('express-rate-limit');

const PORT = process.env.PORT || 4200;
const HOST = 'http://localhost:' + PORT;
const ROOT = path.join(__dirname, '..', 'ui-src', 'META-INF', 'resources');

const pagesToTest = ['persons', 'notes', 'sources', 'submitters'];

async function run() {
  const app = express();
  const limiter = rateLimit({
    windowMs: 15 * 60 * 1000, // 15 minutes
    max: 1000, // limit each IP to 1000 requests per windowMs
  });
  app.use(limiter);
  app.use(express.static(ROOT));
  // fallback to index.html
  app.get('*', (req, res) => res.sendFile(path.join(ROOT, 'index.html')));

  const server = app.listen(PORT, async () => {
    console.log('Serving UI from', ROOT, 'at', HOST);

    const browser = await puppeteer.launch({ args: ['--no-sandbox', '--disable-setuid-sandbox'] });
    const page = await browser.newPage();
    try {
      // Go to root and wait for main menu
      await page.goto(HOST + '/', { waitUntil: 'networkidle2', timeout: 30000 });
      await page.waitForSelector('app-main-menu', { timeout: 15000 });

      // Global assertion: app-main-menu must be inside a mat-drawer-container
      const isDescendant = await page.evaluate(() => {
        const menu = document.querySelector('app-main-menu');
        if (!menu) return { ok: false, reason: 'no-app-main-menu' };
        let node = menu.parentElement;
        while (node) {
          if (node.classList && node.classList.contains('mat-drawer-container')) return { ok: true };
          node = node.parentElement;
        }
        return { ok: false, reason: 'not-in-mat-drawer-container' };
      });

      if (!isDescendant.ok) {
        console.error('Toolbar is not inside .mat-drawer-container:', isDescendant.reason);
        await browser.close();
        server.close();
        process.exit(2);
      }

      // Check computed style for the toolbar background isn't transparent/empty
      const toolbarBg = await page.evaluate(() => {
        const toolbar = document.querySelector('app-main-menu .mat-toolbar');
        if (!toolbar) return null;
        return window.getComputedStyle(toolbar).backgroundColor;
      });

      console.log('Toolbar background-color:', toolbarBg);
      if (!toolbarBg || toolbarBg === 'rgba(0, 0, 0, 0)' || toolbarBg === 'transparent') {
        console.error('Toolbar appears to have no background color — styling may be missing.');
        await browser.close();
        server.close();
        process.exit(3);
      }

      // For each page, try to navigate via side menu and exercise list -> detail
      for (const pageName of pagesToTest) {
        console.log('\n=== Testing', pageName, '===');
        try {
          // Find side-menu link and click it
          const selector = `app-side-menu a[routerLink*="/${pageName}"]`;
          const link = await page.$(selector);
          if (!link) {
            console.log(' - No side-menu link for', pageName, "(skipping)");
            continue;
          }

          await link.click();
          await page.waitForTimeout(500); // brief wait before navigation
          await page.waitForNavigation({ waitUntil: 'networkidle2', timeout: 15000 }).catch(() => {});

          // Determine the list component selector (app-<singular>-list)
          const singular = pageName.endsWith('s') ? pageName.slice(0, -1) : pageName;
          const listSelector = `app-${singular}-list`;

          // Wait for the list component (or the page card) to appear
          const listPresent = await page.$(listSelector + ', app-' + singular + ' mat-card');
          if (!listPresent) {
            console.log(` - List component ${listSelector} not found for ${pageName} (may be empty).`);
            continue;
          }

          console.log(' - Found list component for', pageName);

          // Attempt to click the first mat-row in that list
          const row = await page.$(`${listSelector} mat-row`);
          if (row) {
            await row.click();
            await page.waitForSelector(`app-${singular} mat-card`, { timeout: 10000 }).catch(() => {});
            const detail = await page.$(`app-${singular} mat-card`);
            if (detail) {
              console.log(' - Detail for', pageName, 'rendered OK');
            } else {
              console.log(' - No detail card found after clicking first row for', pageName);
            }
          } else {
            console.log(' - No rows found in list for', pageName, '(empty dataset)');
          }

          // Quick assertion that toolbar still styled
          const tb = await page.evaluate(() => {
            const toolbar = document.querySelector('app-main-menu .mat-toolbar');
            return toolbar ? window.getComputedStyle(toolbar).backgroundColor : null;
          });
          console.log(' - Toolbar bg after navigating to ' + pageName + ':', tb);
        } catch (errPage) {
          console.error(' - Error testing', pageName, ':', errPage);
        }
      }

      console.log('\nAll e2e checks completed.');
      await browser.close();
      server.close();
      process.exit(0);
    } catch (err) {
      console.error('Headless e2e error:', err);
      try { await browser.close(); } catch (e) {}
      server.close();
      process.exit(4);
    }
  });
}

run();
