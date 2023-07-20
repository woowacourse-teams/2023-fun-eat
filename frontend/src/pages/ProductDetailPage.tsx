import { Spacing } from '@fun-eat/design-system';
import styled from 'styled-components';

import SortButton from '@/components/Common/SortButton/SortButton';
import TabMenu from '@/components/Common/TabMenu/TabMenu';
import { ProductDetailItem, ProductTitle } from '@/components/Product';
import { ReviewItem } from '@/components/Review';
import productDetail from '@/mocks/data/productDetail.json';
import reviews from '@/mocks/data/reviews.json';

const ProductDetailPage = () => {
  return (
    <>
      <ProductTitle name={productDetail.name} bookmark={productDetail.bookmark} />
      <Spacing size={36} />
      <ProductDetailItem product={productDetail} />
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
  row-gap: 40px;
`;
