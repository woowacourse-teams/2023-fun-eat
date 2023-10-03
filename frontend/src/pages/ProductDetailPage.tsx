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
  RegisterButton,
  SectionTitle,
} from '@/components/Common';
import { ProductDetailItem, ProductRecipeList } from '@/components/Product';
import { ReviewList, ReviewRegisterForm } from '@/components/Review';
import { RECIPE_SORT_OPTIONS, REVIEW_SORT_OPTIONS } from '@/constants';
import { PATH } from '@/constants/path';
import ReviewFormProvider from '@/contexts/ReviewFormContext';
import { useGA, useSortOption, useTabMenu } from '@/hooks/common';
import { useMemberQuery } from '@/hooks/queries/members';
import { useProductDetailQuery } from '@/hooks/queries/product';

const LOGIN_ERROR_MESSAGE_REVIEW =
  '로그인 후 상품 리뷰를 볼 수 있어요.\n펀잇에 가입하고 편의점 상품 리뷰를 확인해보세요 😊';
const LOGIN_ERROR_MESSAGE_RECIPE =
  '로그인 후 상품 꿀조합을 볼 수 있어요.\n펀잇에 가입하고 편의점 상품 꿀조합을 확인해보세요 😊';

export const ProductDetailPage = () => {
  const { category, productId } = useParams();
  const { data: member } = useMemberQuery();
  const { data: productDetail } = useProductDetailQuery(Number(productId));
  const { reset } = useQueryErrorResetBoundary();

  const { selectedTabMenu, isFirstTabMenu: isReviewTab, handleTabMenuClick, initTabMenu } = useTabMenu();
  const tabRef = useRef<HTMLUListElement>(null);

  const { selectedOption, selectSortOption } = useSortOption(REVIEW_SORT_OPTIONS[0]);
  const { ref, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const [activeSheet, setActiveSheet] = useState<'registerReview' | 'sortOption'>('sortOption');
  const { gaEvent } = useGA();

  const productDetailPageRef = useRef<HTMLDivElement>(null);

  if (!category) {
    return null;
  }

  const { name, bookmark, reviewCount } = productDetail;

  const tabMenus = [`리뷰 ${reviewCount}`, '꿀조합'];
  const sortOptions = isReviewTab ? REVIEW_SORT_OPTIONS : RECIPE_SORT_OPTIONS;
  const currentSortOption = isReviewTab ? REVIEW_SORT_OPTIONS[0] : RECIPE_SORT_OPTIONS[0];

  const handleOpenRegisterReviewSheet = () => {
    setActiveSheet('registerReview');
    handleOpenBottomSheet();
    gaEvent({ category: 'button', action: '상품 리뷰 작성하기 버튼 클릭', label: '상품 리뷰 작성' });
  };

  const handleOpenSortOptionSheet = () => {
    setActiveSheet('sortOption');
    handleOpenBottomSheet();
    gaEvent({ category: 'button', action: '상품 리뷰 정렬 버튼 클릭', label: '상품 리뷰 정렬' });
  };

  const handleTabMenuSelect = (index: number) => {
    handleTabMenuClick(index);
    selectSortOption(currentSortOption);
  };

  return (
    <ProductDetailPageContainer ref={productDetailPageRef}>
      <SectionTitle name={name} bookmark={bookmark} />
      <Spacing size={36} />
      <ProductDetailItem category={category} productDetail={productDetail} />
      <Spacing size={36} />
      <TabMenu
        ref={tabRef}
        tabMenus={tabMenus}
        selectedTabMenu={selectedTabMenu}
        handleTabMenuSelect={handleTabMenuSelect}
      />
      {member ? (
        <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
          <Suspense fallback={<Loading />}>
            <SortButtonWrapper>
              <SortButton option={selectedOption} onClick={handleOpenSortOptionSheet} />
            </SortButtonWrapper>
            <section>
              {isReviewTab ? (
                <ReviewList productId={Number(productId)} selectedOption={selectedOption} />
              ) : (
                <ProductRecipeList productId={Number(productId)} productName={name} selectedOption={selectedOption} />
              )}
            </section>
          </Suspense>
        </ErrorBoundary>
      ) : (
        <ErrorContainer>
          <ErrorDescription align="center" weight="bold" size="lg">
            {isReviewTab ? LOGIN_ERROR_MESSAGE_REVIEW : LOGIN_ERROR_MESSAGE_RECIPE}
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
      <ScrollButton targetRef={productDetailPageRef} />
      <BottomSheet maxWidth="600px" ref={ref} isClosing={isClosing} close={handleCloseBottomSheet}>
        {activeSheet === 'registerReview' ? (
          <ReviewFormProvider>
            <ReviewRegisterForm
              targetRef={tabRef}
              productId={Number(productId)}
              closeReviewDialog={handleCloseBottomSheet}
              initTabMenu={initTabMenu}
            />
          </ReviewFormProvider>
        ) : (
          <SortOptionList
            options={sortOptions}
            selectedOption={selectedOption}
            selectSortOption={selectSortOption}
            close={handleCloseBottomSheet}
          />
        )}
      </BottomSheet>
    </ProductDetailPageContainer>
  );
};

const ProductDetailPageContainer = styled.div`
  height: 100%;
  overflow-y: auto;

  &::-webkit-scrollbar {
    display: none;
  }
`;

const SortButtonWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin: 20px 0;
`;

const ErrorContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const ErrorDescription = styled(Text)`
  padding: 40px 0 20px;
  white-space: pre-wrap;
`;

const LoginLink = styled(Link)`
  padding: 16px 24px;
  border: 1px solid ${({ theme }) => theme.colors.gray4};
  border-radius: 8px;
`;

const ReviewRegisterButtonWrapper = styled.div`
  position: fixed;
  left: 50%;
  bottom: 0;
  width: calc(100% - 40px);
  height: 80px;
  max-width: 560px;
  background: ${({ theme }) => theme.backgroundColors.default};
  transform: translateX(-50%);
`;
