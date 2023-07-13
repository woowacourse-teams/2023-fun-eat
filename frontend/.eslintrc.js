module.exports = {
  env: {
    browser: true,
    es2021: true
  },
  extends: ['eslint:recommended', 'plugin:@typescript-eslint/recommended', 'plugin:react/recommended', 'plugin:storybook/recommended'],
  ignorePatterns: ['*.js'],
  overrides: [{
    env: {
      node: true
    },
    files: ['.eslintrc.{js,cjs}'],
    parserOptions: {
      sourceType: 'script'
    }
  }],
  parser: '@typescript-eslint/parser',
  parserOptions: {
    ecmaVersion: 'latest',
    sourceType: 'module',
    project: './tsconfig.json',
    tsconfigRootDir: __dirname
  },
  plugins: ['@typescript-eslint', 'react'],
  rules: {
    'react/react-in-jsx-scope': 'off',
    '@typescript-eslint/no-var-requires': 0,
    '@typescript-eslint/consistent-type-imports': ['error', {
      prefer: 'type-imports',
      disallowTypeAnnotations: false
    }],
    'react/jsx-key': ['error', {
      warnOnDuplicates: true
    }],
    'react/self-closing-comp': ['error', {
      component: true,
      html: true
    }]
  }
};