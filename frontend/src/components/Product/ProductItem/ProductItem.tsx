import { Spacing, Text, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import productImage from './mock_img.jpg';

const MOCK_PRODUCT = {
  id: 1,
  name: '삼립)달곰이꿀데니쉬',
  price: 1500,
  image: productImage,
  averageRating: 4.5,
  reviewCount: 100,
} as const;

const ProductItem = () => {
  const { name, price, image, averageRating, reviewCount } = MOCK_PRODUCT;

  return (
    <ProductItemContainer>
      <img src={image} width={90} height={90} alt={name} />
      <ProductInfoWrapper>
        <Text size="lg" weight="bold">
          {name}
        </Text>
        <Text color={theme.textColors.info}>{price.toLocaleString('ko-KR')}원</Text>
        <ProductReviewWrapper>
          <Text>😊 {averageRating}</Text>
          <Text>✍️ {reviewCount}</Text>
        </ProductReviewWrapper>
      </ProductInfoWrapper>
    </ProductItemContainer>
  );
};

export default ProductItem;

const ProductItemContainer = styled.div`
  display: flex;
  align-items: center;
  height: 120px;
  padding: 15px;
`;

const ProductInfoWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
  margin-left: 30px;
`;

const ProductReviewWrapper = styled.div`
  display: flex;
  column-gap: 20px;
`;
