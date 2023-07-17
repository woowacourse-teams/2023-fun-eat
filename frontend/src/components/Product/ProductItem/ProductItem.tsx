import { Text, useTheme } from '@fun-eat/design-system';
import styled from 'styled-components';

import type { Product } from '@/types/product';

interface ProductItemProps {
  product: Product;
}

const ProductItem = ({ product }: ProductItemProps) => {
  const theme = useTheme();
  const { name, price, image, averageRating, reviewCount } = product;

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
  height: 150px;
  padding: 30px;
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
