import { BottomSheet, Spacing } from '@fun-eat/design-system';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

import { SortButton, SortOptionList, TabMenu } from '@/components/Common';
import { ProductDetailItem, ProductTitle } from '@/components/Product';
import { ReviewItem } from '@/components/Review';
import { REVIEW_SORT_OPTIONS } from '@/constants';
import { useProductReview } from '@/hooks/product';
import useBottomSheet from '@/hooks/useBottomSheet';
import useSortOption from '@/hooks/useSortOption';
import productDetails from '@/mocks/data/productDetails.json';

const ProductDetailPage = () => {
  const { productId } = useParams();
  const { ref, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const { selectedOption, selectSortOption } = useSortOption(REVIEW_SORT_OPTIONS[0]);

  if (!productId) {
    return null;
  }

  // TODO: productId param으로 api 요청 보내면 바뀔 로직
  const targetProductDetail =
    productDetails.find((productDetail) => productDetail.id === Number(productId)) ?? productDetails[0];

  const { data: productReviews } = useProductReview(productId, selectedOption.value);

  if (!productReviews) {
    return null;
  }

  const { reviews } = productReviews;

  return (
    <>
      <ProductTitle name={targetProductDetail.name} bookmark={targetProductDetail?.bookmark} />
      <Spacing size={36} />
      <ProductDetailItem product={targetProductDetail} />
      <Spacing size={36} />
      <TabMenu tabMenus={[`리뷰 ${reviews.length}`, '꿀조합']} />
      <SortButtonWrapper>
        <SortButton option={selectedOption} onClick={handleOpenBottomSheet} />
      </SortButtonWrapper>
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
      <BottomSheet ref={ref} maxWidth="600px" close={handleCloseBottomSheet}>
        <SortOptionList
          options={REVIEW_SORT_OPTIONS}
          selectedOption={selectedOption}
          selectSortOption={selectSortOption}
          close={handleCloseBottomSheet}
        />
      </BottomSheet>
    </>
  );
};

export default ProductDetailPage;

const SortButtonWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-end;
  margin: 20px 0;
`;

const ReviewItemWrapper = styled.ul`
  display: flex;
  flex-direction: column;
  row-gap: 60px;
`;
