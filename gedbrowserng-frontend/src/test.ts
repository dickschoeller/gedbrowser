// This file is the main entry point for Vitest setup
// It initializes the Angular testing environment

// CRITICAL: Import zone.js first, before anything else
import 'zone.js';
import 'zone.js/testing';

// Ensure zone.js is available in the global scope for Angular testing
import { NgZone } from '@angular/core';

import { getTestBed } from '@angular/core/testing';
import {
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting
} from '@angular/platform-browser-dynamic/testing';

// Patch XMLHttpRequest request headers tracking and response handling for templates
const originalOpen = XMLHttpRequest.prototype.open;
const originalSend = XMLHttpRequest.prototype.send;
const originalSetRequestHeader = XMLHttpRequest.prototype.setRequestHeader;

// Track headers for each request
const requestHeaders: { [key: number]: { [header: string]: string } } = {};
let requestId = 0;

XMLHttpRequest.prototype.open = function(method: string, url: string, ...args: any[]) {
  const id = requestId++;
  this._xhrId = id;
  this._originalUrl = url;
  this._originalMethod = method;
  requestHeaders[id] = {};
  return originalOpen.apply(this, [method, url, ...args]);
};

XMLHttpRequest.prototype.setRequestHeader = function(header: string, value: string) {
  const id = (this as any)._xhrId;
  if (id !== undefined && requestHeaders[id]) {
    requestHeaders[id][header] = value;
  }
  return originalSetRequestHeader.apply(this, [header, value]);
};

XMLHttpRequest.prototype.send = function(body?: any) {
  const url = this._originalUrl as string;
  const method = this._originalMethod as string;
  const self = this as any;
  
  // For template/stylesheet requests in tests, return empty content
  if ((url.endsWith('.html') || url.endsWith('.css')) && !url.includes('node_modules')) {
    // Schedule response asynchronously like real XHR
    Promise.resolve().then(() => {
      self.status = 200;
      self.statusText = 'OK';
      self.responseText = '';
      self.response = '';
      self.readyState = 4;
      
      // Trigger load event
      const loadEvent = new ProgressEvent('load', { lengthComputable: true, loaded: 0, total: 0 });
      if (self.onload) {
        try {
          self.onload(loadEvent);
        } catch (e) {}
      }
      
      // Trigger readystatechange
      if (self.onreadystatechange) {
        try {
          self.onreadystatechange(new Event('readystatechange'));
        } catch (e) {}
      }
    });
    return;
  }
  
  return originalSend.apply(this, [body]);
};

// Initialize the Angular testing environment
getTestBed().initTestEnvironment(
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting()
);
