import { defineConfig } from 'vitest/config';

export default defineConfig({
  test: {
    globals: true,
    environment: 'jsdom',
    setupFiles: ['src/test.ts'],
    include: ['src/**/*.spec.ts'],
    exclude: ['node_modules', 'dist', '.idea', '.git', '.cache'],
    // Run tests serially to avoid zone.js conflicts
    poolOptions: {
      threads: {
        singleThread: true,
      },
    },
    environmentOptions: {
      jsdom: {
        resources: 'usable',
      },
    },
    testTimeout: 10000,
    hookTimeout: 10000,
    coverage: {
      provider: 'v8',
      reporter: ['text', 'html', 'lcov'],
      include: ['src/**/*.{ts,tsx}'],
      exclude: [
        'node_modules/',
        'src/test.ts',
        'dist/**',
        'target/**',
        'ui-src/META-INF/resources/**',
        'tools/**',
        '**/*.spec.ts',
        '**/testing/**'
      ],
      // Set global thresholds
      thresholds: {
        statements: 92,
        branches: 85,
        functions: 89,
        lines: 92,
        // Optional: set thresholds per file
        perFile: false
      }
    }
  }
});

