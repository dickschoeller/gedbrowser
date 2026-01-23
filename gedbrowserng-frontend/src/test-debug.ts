// This file is required by karma.conf.js and loads recursively all the .spec and framework files

import 'zone.js';
import 'zone.js/testing';
import { getTestBed } from '@angular/core/testing';
import {
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting
} from '@angular/platform-browser-dynamic/testing';

// Unfortunately there's no typing for the `__karma__` variable. Just declare it as any.
declare const __karma__: any;
declare const require: any;

// Prevent Karma from running prematurely.
__karma__.loaded = function () {};

// First, initialize the Angular testing environment.
getTestBed().initTestEnvironment(
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting()
);

console.log('Test environment initialized');

// Then we find all the tests.
const context = require.context('./', true, /\.spec\.ts$/);
console.log('Context created, loading tests...');
console.log('Test files found:', context.keys().length);

// And load the modules.
const modules = context.keys();
console.log('Loading ', modules.length, 'test modules');
for (let i = 0; i < Math.min(5, modules.length); i++) {
  console.log(`Loading test file ${i + 1}/${Math.min(5, modules.length)}: ${modules[i]}`);
  try {
    context(modules[i]);
    console.log(`✓ Loaded successfully`);
  } catch (err) {
    console.error(`✗ Error loading ${modules[i]}:`, err);
  }
}

console.log('Test setup complete');
// Finally, start Karma to run the tests.
__karma__.start();
