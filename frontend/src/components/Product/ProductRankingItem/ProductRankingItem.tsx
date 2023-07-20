import { Text } from '@fun-eat/design-system';
import styled from 'styled-components';

import type { ProductRanking } from '@/types/ranking';

interface ProductRankingItemProps {
  productRanking: ProductRanking;
}

const ProductRankingItem = ({ productRanking }: ProductRankingItemProps) => {
  const { rank, image, name } = productRanking;

  return (
    <ProductRankingContainer>
      <Text size="lg" weight="bold" align="center">
        {rank}
      </Text>
      <ProductRankingImage src={image} alt={`${rank}위 상품 사진`} />
      <Text size="lg" weight="bold" align="center">
        {name}
      </Text>
    </ProductRankingContainer>
  );
};

export default ProductRankingItem;

const ProductRankingContainer = styled.div`
  display: flex;
  align-items: center;
  width: 300px;
  height: 50px;
  margin-bottom: 15px;
  padding-left: 15px;
  gap: 15px;
  border-radius: ${({ theme }) => theme.borderRadius.xs};
  background: ${({ theme }) => theme.colors.gray1};
`;

const ProductRankingImage = styled.img`
  width: 45px;
  height: 45px;
  border-radius: 50%;
`;
