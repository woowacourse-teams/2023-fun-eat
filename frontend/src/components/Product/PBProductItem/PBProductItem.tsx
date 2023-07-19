import { Text, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import type { PBProduct } from '@/types/product';

interface PBProductItemProps {
  pbProduct: PBProduct;
}

const PBProductItem = ({ pbProduct }: PBProductItemProps) => {
  const { name, price, image, averageRating } = pbProduct;

  return (
    <PBProductItemContainer>
      <PBProductImage src={image} alt={name} />
      <PBProductInfoWrapper>
        <PBProductName weight="bold">{name}</PBProductName>
        <PBProductDetailInfoWrapper>
          <RatingWrapper>
            <SvgIcon variant="star" color={theme.colors.secondary} width={18} height={18} />
            <Text as="span" size="sm" weight="bold" color={theme.textColors.info}>
              {averageRating}
            </Text>
          </RatingWrapper>
          <Text as="span" size="sm" color={theme.textColors.info}>
            {price.toLocaleString('ko-KR')}원
          </Text>
        </PBProductDetailInfoWrapper>
      </PBProductInfoWrapper>
    </PBProductItemContainer>
  );
};

export default PBProductItem;

const PBProductItemContainer = styled.div`
  width: 110px;
  height: 220px;
  margin: 0 20px;
`;

const PBProductImage = styled.img`
  width: 100%;
  height: 50%;
  object-fit: cover;
`;

const PBProductInfoWrapper = styled.div`
  height: 50%;
  margin-top: 10px;
`;

const PBProductName = styled(Text)`
  display: inline-block;
  width: 100%;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
`;

const PBProductDetailInfoWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin: 5px 0;
`;

const RatingWrapper = styled.div`
  display: flex;
  align-items: center;
`;
