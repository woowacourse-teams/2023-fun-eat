import { Text, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import PBPreviewImage from '@/assets/samgakgimbab.svg';
import { SvgIcon } from '@/components/Common';
import type { PBProduct } from '@/types/product';

interface PBProductItemProps {
  pbProduct: PBProduct;
}

const PBProductItem = ({ pbProduct }: PBProductItemProps) => {
  const { name, price, image, averageRating } = pbProduct;

  return (
    <PBProductItemContainer>
      {image ? (
        <PBProductImage src={image} alt={`${name}사진`} width={110} height={110} />
      ) : (
        <PBPreviewImage alt="대체 이미지" width={110} height={110} />
      )}
      <PBProductInfoWrapper>
        <PBProductName weight="bold">{name}</PBProductName>
        <PBProductReviewWrapper>
          <RatingWrapper>
            <SvgIcon variant="star" color={theme.colors.secondary} width={18} height={18} />
            <Text as="span" size="sm" weight="bold" color={theme.textColors.info} aria-label={`${averageRating}점`}>
              {averageRating}
            </Text>
          </RatingWrapper>
          <Text as="span" size="sm" color={theme.textColors.info}>
            {price.toLocaleString('ko-KR')}원
          </Text>
        </PBProductReviewWrapper>
      </PBProductInfoWrapper>
    </PBProductItemContainer>
  );
};

export default PBProductItem;

const PBProductItemContainer = styled.div`
  width: 110px;
`;

const PBProductImage = styled.img`
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

const PBProductReviewWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin: 5px 0;
`;

const RatingWrapper = styled.div`
  display: flex;
  align-items: center;
  column-gap: 4px;

  & > svg {
    padding-bottom: 2px;
  }
`;
