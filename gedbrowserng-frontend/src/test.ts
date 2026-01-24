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

// Initialize the Angular testing environment
getTestBed().initTestEnvironment(
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting()
);
