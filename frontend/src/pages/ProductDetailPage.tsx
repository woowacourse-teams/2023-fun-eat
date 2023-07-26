import { Spacing } from '@fun-eat/design-system';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

import { SortButton, TabMenu } from '@/components/Common';
import { ProductDetailItem, ProductTitle } from '@/components/Product';
import { ReviewItem } from '@/components/Review';
import { useProductDetail } from '@/hooks/product';
import mockReviews from '@/mocks/data/reviews.json';

const ProductDetailPage = () => {
  const { productId } = useParams();

  if (!productId) {
    return null;
  }

  const { data: productDetail } = useProductDetail(productId);

  if (!productDetail) {
    return null;
  }

  const { reviews } = mockReviews;

  return (
    <>
      <ProductTitle name={productDetail.name} bookmark={productDetail.bookmark} />
      <Spacing size={36} />
      <ProductDetailItem product={productDetail} />
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
