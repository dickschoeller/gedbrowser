import tseslint from 'typescript-eslint';
import angularEslintPlugin from '@angular-eslint/eslint-plugin';
import angularTemplatePlugin from '@angular-eslint/eslint-plugin-template';
import angularTemplateParser from '@angular-eslint/template-parser';

export default [
  {
    ignores: [
      'dist/**',
      'target/**',
      'ui-src/META-INF/resources/**',
      'projects/**/*'
    ]
  },
  {
    files: ['src/**/*.ts'],
    languageOptions: {
      parser: tseslint.parser,
      parserOptions: {
        project: ['src/tsconfig.eslint.json'],
        createDefaultProgram: true
      }
    },
    plugins: {
      '@angular-eslint': angularEslintPlugin,
      '@angular-eslint/template': angularTemplatePlugin
    },
    processor: angularTemplatePlugin.processors['extract-inline-html'],
    rules: {
      '@angular-eslint/prefer-standalone': 'off',
      '@angular-eslint/prefer-inject': 'off',
      '@angular-eslint/no-empty-lifecycle-method': 'off'
    }
  },
  {
    files: ['src/**/*.html'],
    languageOptions: {
      parser: angularTemplateParser
    },
    plugins: {
      '@angular-eslint/template': angularTemplatePlugin
    },
    rules: {
      '@angular-eslint/template/prefer-control-flow': 'off'
    }
  }
];
