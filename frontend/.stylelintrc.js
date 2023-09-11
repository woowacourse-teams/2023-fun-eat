const { propertyOrdering, selectorOrdering } = require('stylelint-semantic-groups');

propertyOrdering[0] = propertyOrdering[0].map((rule) => {
  rule.emptyLineBefore = 'never';
  return rule;
});

module.exports = {
  plugins: ['stylelint-order'],
  customSyntax: 'postcss-styled-syntax',
  rules: {
    'order/order': selectorOrdering,
    'order/properties-order': propertyOrdering,
  },
};
