const path = require('path');
const Dotenv = require('dotenv-webpack');

module.exports = {
  entry: './src/index.tsx',
  output: {
    path: path.join(__dirname, 'dist'),
    filename: '[name].[chunkhash].js',
    clean: true,
    publicPath: '/',
  },
  resolve: {
    extensions: ['.ts', '.tsx', '.js', '.json'],
    alias: {
      '@': path.resolve(__dirname, 'src'),
    },
  },
  module: {
    rules: [
      {
        test: /\.(ts|tsx|js)$/,
        exclude: /node_modules/,
        use: [
          {
            loader: 'ts-loader',
          },
        ],
      },
      {
        test: /\.(png|jpeg|jpg)$/,
        type: 'asset/resource',
      },
      {
        test: /\.svg$/,
        issuer: /\.tsx$/,
        use: [{ loader: '@svgr/webpack' }],
      },
    ],
  },
  plugins: [new Dotenv()],
};
