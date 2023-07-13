import type { StorybookConfig } from '@storybook/react-webpack5';
import path from 'path';

const config: StorybookConfig = {
  stories: ['../src/**/*.mdx', '../src/**/*.stories.@(js|jsx|ts|tsx)'],
  addons: ['@storybook/addon-links', '@storybook/addon-essentials', '@storybook/addon-interactions'],
  framework: {
    name: '@storybook/react-webpack5',
    options: {},
  },
  core: {
    builder: {
      name: '@storybook/builder-webpack5',
      options: {
        fsCache: true,
        lazyCompilation: true,
      },
    },
  },
  webpackFinal: async (config) => {
    if (config.resolve) {
      config.resolve.alias = {
        ...config.resolve.alias,
        '@': path.resolve(__dirname, '../src'),
        '@apis': path.resolve(__dirname, '../src/apis'),
        '@assets': path.resolve(__dirname, '../src/assets'),
        '@components': path.resolve(__dirname, '../src/components'),
        '@constants': path.resolve(__dirname, '../src/constants'),
        '@hooks': path.resolve(__dirname, '../src/hooks'),
        '@mocks': path.resolve(__dirname, '../src/mocks'),
        '@pages': path.resolve(__dirname, '../src/pages'),
        '@router': path.resolve(__dirname, '../src/router'),
        '@styles': path.resolve(__dirname, '../src/styles'),
        '@utils': path.resolve(__dirname, '../src/utils'),
      };
    }
    return config;
  },
  docs: {
    autodocs: true,
  },
};
export default config;
