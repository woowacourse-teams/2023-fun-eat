import { BottomSheet, Button, Spacing, useBottomSheet } from '@fun-eat/design-system';
import { useState } from 'react';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

import { SortButton, SortOptionList, TabMenu } from '@/components/Common';
import { ProductDetailItem, ProductTitle } from '@/components/Product';
import { ReviewItem, ReviewRegisterForm } from '@/components/Review';
import { REVIEW_SORT_OPTIONS } from '@/constants';
import { useProductReview, useProductDetail } from '@/hooks/product';
import useSortOption from '@/hooks/useSortOption';

const ProductDetailPage = () => {
  const [activeSheet, setActiveSheet] = useState<'registerReview' | 'sortOption'>('sortOption');

  const { productId } = useParams();
  const { ref, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const { selectedOption, selectSortOption } = useSortOption(REVIEW_SORT_OPTIONS[0]);

  const { data: productDetail } = useProductDetail(productId as string);
  const { data: productReviews } = useProductReview(productId as string, selectedOption.value);

  if (!productDetail || !productReviews) {
    return null;
  }

  const { reviews } = productReviews;

  const handleOpenRegisterReviewSheet = () => {
    setActiveSheet('registerReview');
    handleOpenBottomSheet();
  };

  const handleOpenSortOptionSheet = () => {
    setActiveSheet('sortOption');
    handleOpenBottomSheet();
  };

  return (
    <>
      <ProductTitle name={productDetail.name} bookmark={productDetail.bookmark} />
      <Spacing size={36} />
      <ProductDetailItem product={productDetail} />
      <Spacing size={36} />
      <TabMenu tabMenus={[`리뷰 ${reviews.length}`, '꿀조합']} />
      <SortButtonWrapper>
        <SortButton option={selectedOption} onClick={handleOpenSortOptionSheet} />
      </SortButtonWrapper>
      <section>
        {reviews && (
          <ReviewItemWrapper>
            {reviews.map((review) => (
              <li key={review.id}>
                <ReviewItem productId={Number(productId)} review={review} />
              </li>
            ))}
          </ReviewItemWrapper>
        )}
      </section>
      <Spacing size={100} />
      <ReviewRegisterButtonWrapper>
        <Button
          type="button"
          customWidth="100%"
          customHeight="60px"
          size="xl"
          weight="bold"
          onClick={handleOpenRegisterReviewSheet}
        >
          리뷰 작성하기
        </Button>
      </ReviewRegisterButtonWrapper>
      <BottomSheet maxWidth="600px" ref={ref} isClosing={isClosing} close={handleCloseBottomSheet}>
        {activeSheet === 'registerReview' ? (
          <ReviewRegisterForm product={productDetail} close={handleCloseBottomSheet} />
        ) : (
          <SortOptionList
            options={REVIEW_SORT_OPTIONS}
            selectedOption={selectedOption}
            selectSortOption={selectSortOption}
            close={handleCloseBottomSheet}
          />
        )}
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

const ReviewRegisterButtonWrapper = styled.div`
  position: fixed;
  bottom: 0;
  left: 50%;
  width: calc(100% - 40px);
  max-width: 560px;
  height: 80px;
  background: ${({ theme }) => theme.backgroundColors.default};
  transform: translateX(-50%);
`;
