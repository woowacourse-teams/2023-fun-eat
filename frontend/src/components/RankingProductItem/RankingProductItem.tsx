import { Text } from '@fun-eat/design-system';
import styled from 'styled-components';

import type { RankingProduct } from '@types';

interface RankingProductItemProps {
  rankingProduct: RankingProduct;
}

const RankingProductItem = ({ rankingProduct }: RankingProductItemProps) => {
  const { rank, image, name } = rankingProduct;

  return (
    <RankingProductContainer>
      <Text size="lg" weight="bold">
        {rank}
      </Text>
      <RankingProductImage src={image} alt={`${rank}위 상품 사진`} />
      <Text size="lg" weight="bold">
        {name}
      </Text>
    </RankingProductContainer>
  );
};

export default RankingProductItem;

const RankingProductContainer = styled.li`
  display: flex;
  align-items: center;
  width: 300px;
  height: 50px;
  margin-bottom: 15px;
  padding-left: 15px;
  gap: 15px;
  border-radius: ${({ theme }) => theme.borderRadius.xs};
  background: ${({ theme }) => theme.colors.gray1};
  text-align: center;
`;

const RankingProductImage = styled.img`
  width: 45px;
  height: 45px;
  border-radius: 50%;
`;
