import { BottomSheet, Button, Spacing, useBottomSheet } from '@fun-eat/design-system';
import { useState } from 'react';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

import { SortButton, SortOptionList, TabMenu } from '@/components/Common';
import { ProductDetailItem } from '@/components/Product';
import { ReviewList, ReviewRegisterForm } from '@/components/Review';
import { REVIEW_SORT_OPTIONS } from '@/constants';
import ReviewFormProvider from '@/contexts/ReviewFormContext';
import { useMemberValueContext } from '@/hooks/context';
import useSortOption from '@/hooks/useSortOption';

const ProductDetailPage = () => {
  const [activeSheet, setActiveSheet] = useState<'registerReview' | 'sortOption'>('sortOption');

  const { productId } = useParams();
  const { ref, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const { selectedOption, selectSortOption } = useSortOption(REVIEW_SORT_OPTIONS[0]);

  const member = useMemberValueContext();

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
      <ProductDetailItem productId={Number(productId)} />
      <Spacing size={36} />
      {/* 나중에 API 수정하면 이 부분도 같이 수정해주세요 */}
      <TabMenu tabMenus={['리뷰 10', '꿀조합']} />
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
      <BottomSheet maxWidth="600px" ref={ref} isClosing={isClosing} close={handleCloseBottomSheet}>
        {activeSheet === 'registerReview' ? (
          <ReviewFormProvider>
            <ReviewRegisterForm productId={Number(productId)} closeReviewDialog={handleCloseBottomSheet} />
          </ReviewFormProvider>
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
