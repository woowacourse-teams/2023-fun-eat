import { Spacing } from '@fun-eat/design-system';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

import { SortButton, TabMenu } from '@/components/Common';
import { ProductDetailItem, ProductTitle } from '@/components/Product';
import { ReviewItem } from '@/components/Review';
import productDetails from '@/mocks/data/productDetails.json';
import mockReviews from '@/mocks/data/reviews.json';

const ProductDetailPage = () => {
  const { productId } = useParams();

  // TODO: productId param으로 api 요청 보내면 바뀔 로직
  const targetProductDetail =
    productDetails.find((productDetail) => productDetail.id === Number(productId)) ?? productDetails[0];

  const { reviews } = mockReviews;

  return (
    <>
      <ProductTitle name={targetProductDetail.name} bookmark={targetProductDetail?.bookmark} />
      <Spacing size={36} />
      <ProductDetailItem product={targetProductDetail} />
      <Spacing size={36} />
      <TabMenu tabMenus={[`리뷰 ${reviews.length}`, '꿀조합']} />
      <Spacing size={8} />
      <SortButtonWrapper>
        <SortButton />
      </SortButtonWrapper>
      <Spacing size={8} />
      <section>
        {reviews && (
          <ReviewItemWrapper>
            {reviews.map((review) => (
              <li key={review.id}>
                <ReviewItem review={review} />
              </li>
            ))}
          </ReviewItemWrapper>
        )}
      </section>
    </>
  );
};

export default ProductDetailPage;

const SortButtonWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-end;
`;

const ReviewItemWrapper = styled.ul`
  display: flex;
  flex-direction: column;
  row-gap: 60px;
`;
