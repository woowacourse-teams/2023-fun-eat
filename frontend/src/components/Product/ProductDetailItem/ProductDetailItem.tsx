import { Text, useTheme } from '@fun-eat/design-system';
import styled from 'styled-components';

import PreviewImage from '@/assets/characters.svg';
import PBPreviewImage from '@/assets/samgakgimbab.svg';
import { SvgIcon, TagList } from '@/components/Common';
import { CATEGORY_TYPE } from '@/constants';
import type { ProductDetail } from '@/types/product';

interface ProductDetailItemProps {
  category: string;
  productDetail: ProductDetail;
}

const ProductDetailItem = ({ category, productDetail }: ProductDetailItemProps) => {
  const { name, price, image, content, averageRating, tags } = productDetail;

  const theme = useTheme();

  return (
    <ProductDetailContainer>
      {image !== null ? (
        <img src={image} width={300} alt={name} />
      ) : category === CATEGORY_TYPE.FOOD ? (
        <PreviewImage width={300} />
      ) : (
        <PBPreviewImage width={300} />
      )}
      <DetailInfoWrapper>
        <DescriptionWrapper>
          <Text weight="bold">가격</Text>
          <Text>{price.toLocaleString('ko-KR')}원</Text>
        </DescriptionWrapper>
        <DescriptionWrapper>
          <Text weight="bold">상품 설명</Text>
          <Text>{content}</Text>
        </DescriptionWrapper>
        <DescriptionWrapper aria-label={`평균 평점 ${averageRating}점`}>
          <Text weight="bold">평균 평점</Text>
          <RatingIconWrapper>
            <SvgIcon variant="star" width={20} height={20} color={theme.colors.secondary} />
            <Text as="span">{averageRating}</Text>
          </RatingIconWrapper>
        </DescriptionWrapper>
      </DetailInfoWrapper>
      <TagList tags={tags} />
    </ProductDetailContainer>
  );
};

export default ProductDetailItem;

const ProductDetailContainer = styled.div`
  display: flex;
  flex-direction: column;
  row-gap: 30px;
  g & > img,
  svg {
    align-self: center;
  }
`;

const DetailInfoWrapper = styled.div`
  & > div + div {
    margin-top: 10px;
  }
`;

const DescriptionWrapper = styled.div`
  display: flex;
  column-gap: 20px;

  & > p:first-of-type {
    flex-shrink: 0;
    width: 60px;
  }
`;

const RatingIconWrapper = styled.div`
  display: flex;
  align-items: center;
  column-gap: 4px;
  margin-left: -4px;

  & > svg {
    padding-bottom: 2px;
  }
`;
