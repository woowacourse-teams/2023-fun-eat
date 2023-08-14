import { BottomSheet, Spacing, useBottomSheet, Text, Link } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { useState, useRef, Suspense } from 'react';
import { useParams, Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import {
  SortButton,
  SortOptionList,
  TabMenu,
  ScrollButton,
  Loading,
  ErrorBoundary,
  ErrorComponent,
} from '@/components/Common';
import RegisterButton from '@/components/Common/RegisterButton/RegisterButton';
import { ProductDetailItem } from '@/components/Product';
import { ReviewList, ReviewRegisterForm } from '@/components/Review';
import { REVIEW_SORT_OPTIONS } from '@/constants';
import { PATH } from '@/constants/path';
import ReviewFormProvider from '@/contexts/ReviewFormContext';
import { useSortOption } from '@/hooks/common';
import { useMemberValueContext } from '@/hooks/context';

const LOGIN_ERROR_MESSAGE =
  '로그인 해야 상품 리뷰를 볼 수 있어요.\n펀잇에 가입하고 편의점 상품의 리뷰를 확인해보세요 😊';

const ProductDetailPage = () => {
  const [activeSheet, setActiveSheet] = useState<'registerReview' | 'sortOption'>('sortOption');
  const tabRef = useRef<HTMLUListElement>(null);
  const { productId } = useParams();
  const { ref, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const { selectedOption, selectSortOption } = useSortOption(REVIEW_SORT_OPTIONS[0]);
  const { reset } = useQueryErrorResetBoundary();

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
      <TabMenu ref={tabRef} tabMenus={['리뷰 10', '꿀조합']} />
      {member ? (
        <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
          <Suspense fallback={<Loading />}>
            <SortButtonWrapper>
              <SortButton option={selectedOption} onClick={handleOpenSortOptionSheet} />
            </SortButtonWrapper>
            <section>
              <ReviewList productId={Number(productId)} selectedOption={selectedOption} />
            </section>
          </Suspense>
        </ErrorBoundary>
      ) : (
        <ErrorContainer>
          <ErrorDescription align="center" weight="bold" size="lg">
            {LOGIN_ERROR_MESSAGE}
          </ErrorDescription>
          <LoginLink as={RouterLink} to={PATH.LOGIN} block>
            로그인하러 가기
          </LoginLink>
        </ErrorContainer>
      )}
      <Spacing size={100} />
      <ReviewRegisterButtonWrapper>
        <RegisterButton
          activeLabel="리뷰 작성하기"
          disabledLabel="로그인 후 리뷰를 작성할 수 있어요"
          onClick={handleOpenRegisterReviewSheet}
        />
      </ReviewRegisterButtonWrapper>
      <ScrollButton />
      <BottomSheet maxWidth="600px" ref={ref} isClosing={isClosing} close={handleCloseBottomSheet}>
        {activeSheet === 'registerReview' ? (
          <ReviewFormProvider>
            <ReviewRegisterForm
              targetRef={tabRef}
              productId={Number(productId)}
              closeReviewDialog={handleCloseBottomSheet}
            />
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

const ErrorContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const ErrorDescription = styled(Text)`
  padding: 40px 0;
  white-space: pre-line;
  word-break: break-all;
`;

const LoginLink = styled(Link)`
  padding: 16px 24px;
  border: 1px solid ${({ theme }) => theme.colors.gray4};
  border-radius: 8px;
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
