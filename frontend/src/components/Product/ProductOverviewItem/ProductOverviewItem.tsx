import { Text } from '@fun-eat/design-system';
import styled from 'styled-components';

import type { ProductRanking } from '@/types/ranking';

interface ProductRankingItemProps {
  productRanking: ProductRanking;
  variant: 'default' | 'ranking';
}

const ProductOverviewItem = ({ productRanking, variant }: ProductRankingItemProps) => {
  const { rank, image, name } = productRanking;

  return (
    <ProductOverviewContainer variant={variant}>
      {variant === 'ranking' && (
        <Text size="lg" weight="bold" align="center">
          {rank}
        </Text>
      )}
      <ProductOverviewImage src={image} alt={`${rank}위 상품`} />
      <Text size="lg" weight="bold" align="center">
        {name}
      </Text>
    </ProductOverviewContainer>
  );
};

export default ProductOverviewItem;

const ProductOverviewContainer = styled.div<{ variant: 'default' | 'ranking' }>`
  display: flex;
  align-items: center;
  height: 50px;
  margin-bottom: 15px;
  padding-left: 15px;
  gap: 15px;
  border-radius: ${({ theme }) => theme.borderRadius.xs};
  background: ${({ theme, variant }) => (variant === 'default' ? theme.colors.white : theme.colors.gray1)};
`;

const ProductOverviewImage = styled.img`
  width: 45px;
  height: 45px;
  border-radius: 50%;
`;
