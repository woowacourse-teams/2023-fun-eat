import { Text, useTheme } from '@fun-eat/design-system';
import { useState } from 'react';
import styled from 'styled-components';

import PreviewImage from '@/assets/characters.svg';
import { Skeleton, SvgIcon } from '@/components/Common';
import type { Product } from '@/types/product';

interface ProductItemProps {
  product: Product;
}

const ProductItem = ({ product }: ProductItemProps) => {
  const theme = useTheme();
  const { name, price, image, averageRating, reviewCount } = product;
  const [isImageLoading, setIsImageLoading] = useState(true);

  return (
    <ProductItemContainer>
      {image !== null ? (
        <>
          <ProductImage
            src={image}
            width={90}
            height={90}
            alt={`${name}사진`}
            loading="lazy"
            onLoad={() => setIsImageLoading(false)}
          />
          {isImageLoading && <Skeleton width={90} height={90} />}
        </>
      ) : (
        <PreviewImage width={90} height={90} />
      )}
      <ProductInfoWrapper>
        <Text size="lg" weight="bold">
          {name}
        </Text>
        <Text size="sm" color={theme.textColors.info}>
          {price.toLocaleString('ko-KR')}원
        </Text>
        <ProductReviewWrapper>
          <RatingIconWrapper>
            <SvgIcon variant="star" width={20} height={20} color={theme.colors.secondary} />
            <Text as="span" size="sm" css="line-height: 24px;" aria-label={`${averageRating}점`}>
              {averageRating}
            </Text>
          </RatingIconWrapper>
          <ReviewIconWrapper>
            <SvgIcon variant="review" width={20} height={20} color={theme.colors.gray5} />
            <Text as="span" size="sm" css="line-height: 24px" aria-label={`리뷰 ${reviewCount}개`}>
              {reviewCount}
            </Text>
          </ReviewIconWrapper>
        </ProductReviewWrapper>
      </ProductInfoWrapper>
    </ProductItemContainer>
  );
};

export default ProductItem;

const ProductItemContainer = styled.div`
  position: relative;
  display: flex;
  align-items: center;
  padding: 12px 0;
`;

const ProductImage = styled.img`
  object-fit: cover;
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
  margin-left: -2px;
  column-gap: 20px;
`;

const RatingIconWrapper = styled.div`
  display: flex;
  align-items: center;
  column-gap: 4px;

  & > svg {
    padding-bottom: 2px;
  }
`;

const ReviewIconWrapper = styled.div`
  display: flex;
  align-items: center;
  column-gap: 4px;

  & > svg {
    padding-top: 2px;
  }
`;
