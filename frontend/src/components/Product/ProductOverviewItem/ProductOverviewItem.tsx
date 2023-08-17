import { Text } from '@fun-eat/design-system';
import styled from 'styled-components';

import PreviewImage from '@/assets/characters.svg';
import { IMAGE_SRC_PATH } from '@/constants/path';

interface ProductOverviewItemProps {
  name: string;
  image: string | null;
  rank?: number;
}

const ProductOverviewItem = ({ name, image, rank }: ProductOverviewItemProps) => {
  return (
    <ProductOverviewContainer rank={rank} tabIndex={0}>
      <Text size="lg" weight="bold" align="center">
        {rank ?? ''}
      </Text>
      {image !== null ? (
        <ProductOverviewImage src={IMAGE_SRC_PATH + image} alt={rank ? `${rank}위 상품` : `${name}사진`} />
      ) : (
        <ProductPreviewImage width={45} height={45} />
      )}
      <Text size="lg" weight="bold" align="center" aria-label={name}>
        {name}
      </Text>
    </ProductOverviewContainer>
  );
};

export default ProductOverviewItem;

const ProductOverviewContainer = styled.div<Pick<ProductOverviewItemProps, 'rank'>>`
  display: flex;
  align-items: center;
  height: 50px;
  padding-left: 15px;
  gap: 15px;
  border-radius: ${({ theme }) => theme.borderRadius.xs};
  background: ${({ theme, rank }) => (rank ? theme.colors.gray1 : theme.colors.white)};
`;

const ProductOverviewImage = styled.img`
  width: 45px;
  height: 45px;
  border-radius: 50%;
`;

const ProductPreviewImage = styled(PreviewImage)`
  width: 45px;
  height: 45px;
  border-radius: 50%;
  background-color: ${({ theme }) => theme.colors.white};
`;
