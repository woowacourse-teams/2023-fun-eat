import { BottomSheet, Button, Spacing, useBottomSheet } from '@fun-eat/design-system';
import { useState } from 'react';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

import { SortButton, SortOptionList, TabMenu } from '@/components/Common';
import ScrollButton from '@/components/Common/ScrollButton/ScrollButton';
import { ProductDetailItem, ProductTitle } from '@/components/Product';
import { ReviewList, ReviewRegisterForm } from '@/components/Review';
import { REVIEW_SORT_OPTIONS } from '@/constants';
import { useMemberValueContext, useProductReviewContext } from '@/hooks/context';
import { useProductDetail } from '@/hooks/product';
import useSortOption from '@/hooks/useSortOption';

const ProductDetailPage = () => {
  const [activeSheet, setActiveSheet] = useState<'registerReview' | 'sortOption'>('sortOption');
  const { productReviews } = useProductReviewContext();

  const { productId } = useParams();
  const { ref, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const { selectedOption, selectSortOption } = useSortOption(REVIEW_SORT_OPTIONS[0]);

  const member = useMemberValueContext();

  const { data: productDetail } = useProductDetail(productId as string);

  if (!productDetail) {
    return null;
  }

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
      <TabMenu tabMenus={[`리뷰 ${productReviews.length}`, '꿀조합']} />
      <SortButtonWrapper>
        <SortButton option={selectedOption} onClick={handleOpenSortOptionSheet} />
      </SortButtonWrapper>
      <section>
        <ReviewList productId={Number(productId)} selectedOption={selectedOption} />
      </section>
      <Spacing size={100} />
      <ReviewRegisterButtonWrapper>
        <ReviewRegisterButton
          type="button"
          customWidth="100%"
          customHeight="60px"
          color={!member ? 'gray3' : 'primary'}
          textColor={!member ? 'white' : 'default'}
          size="xl"
          weight="bold"
          onClick={handleOpenRegisterReviewSheet}
          disabled={!member}
        >
          {member ? '리뷰 작성하기' : '로그인 후 리뷰를 작성할 수 있어요'}
        </ReviewRegisterButton>
      </ReviewRegisterButtonWrapper>
      <ScrollButton />
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

const ReviewRegisterButton = styled(Button)`
  cursor: ${({ disabled }) => (disabled ? 'not-allowed' : 'pointer')};
`;
