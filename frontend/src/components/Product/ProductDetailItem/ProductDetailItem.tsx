import { Text } from '@fun-eat/design-system';
import styled, { useTheme } from 'styled-components';

import { SvgIcon } from '@/components/Common';
import TagList from '@/components/Common/TagList/TagList';
import type { ProductDetail } from '@/types/product';

interface ProductDetailProps {
  product: ProductDetail;
}

const ProductDetailItem = ({ product }: ProductDetailProps) => {
  const { name, price, image, content, averageRating, tags } = product;
  const theme = useTheme();

  return (
    <ProductDetailContainer>
      <img src={image} width={300} alt={name} />
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

  & > img {
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
