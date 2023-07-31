module.exports = {
  testEnvironment: 'jsdom',
  transform: {
    '^.+\\.(js|ts|tsx)?$': 'ts-jest',
  },
  moduleNameMapper: {
    '^@/(.*)$': '<rootDir>/src/$1',
  },
  testMatch: ['<rootDir>/__tests__/**/*.test.(js|jsx|ts|tsx)'],
};
