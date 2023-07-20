import { Spacing } from '@fun-eat/design-system';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

import { SortButton, TabMenu } from '@/components/Common';
import { ProductDetailItem, ProductTitle } from '@/components/Product';
import { ReviewItem } from '@/components/Review';
import productDetails from '@/mocks/data/productDetails.json';
import reviews from '@/mocks/data/reviews.json';

const ProductDetailPage = () => {
  const { productId } = useParams();

  // TODO: productId param으로 api 요청 보내면 바뀔 로직
  const targetProductDetail =
    productDetails.find((productDetail) => productDetail.id === Number(productId)) ?? productDetails[0];

  return (
    <>
      <ProductTitle name={targetProductDetail.name} bookmark={targetProductDetail?.bookmark} />
      <Spacing size={36} />
      <ProductDetailItem product={targetProductDetail} />
      <Spacing size={36} />
      <TabMenu tabMenus={[`리뷰 ${reviews.length}`, '꿀조합']} />
      <Spacing size={30} />
      <ReviewSection>
        <SortButtonWrapper>
          <SortButton />
        </SortButtonWrapper>
        <Spacing size={30} />
        {reviews && (
          <ReviewItemWrapper>
            {reviews.map((review) => (
              <li key={review.id}>
                <ReviewItem review={review} />
              </li>
            ))}
          </ReviewItemWrapper>
        )}
      </ReviewSection>
    </>
  );
};

export default ProductDetailPage;

const ReviewSection = styled.section`
  position: relative;
`;

const SortButtonWrapper = styled.div`
  position: absolute;
  top: -24px;
  right: 0;
`;

const ReviewItemWrapper = styled.li`
  display: flex;
  flex-direction: column;
  row-gap: 60px;
`;
