import { BottomSheet, Button, Spacing, useBottomSheet } from '@fun-eat/design-system';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

import { SortButton, TabMenu } from '@/components/Common';
import { ProductDetailItem, ProductTitle } from '@/components/Product';
import { ReviewItem } from '@/components/Review';
import ReviewRegisterForm from '@/components/Review/ReviewRegisterForm/ReviewRegisterForm';
import productDetails from '@/mocks/data/productDetails.json';
import mockReviews from '@/mocks/data/reviews.json';

const ProductDetailPage = () => {
  const { productId } = useParams();
  const { ref, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();

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
      <Spacing size={100} />
      <ReviewRegisterButtonWrapper>
        <Button type="button" width="100%" height="60px" size="xl" onClick={handleOpenBottomSheet}>
          리뷰 작성하기
        </Button>
      </ReviewRegisterButtonWrapper>
      <BottomSheet maxWidth="600px" ref={ref} isClosing={isClosing} close={handleCloseBottomSheet}>
        <ReviewRegisterForm product={targetProductDetail} close={handleCloseBottomSheet} />
      </BottomSheet>
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

const ReviewRegisterButtonWrapper = styled.div`
  position: fixed;
  bottom: 60px;
  left: 50%;
  width: calc(100% - 40px);
  max-width: 560px;
  height: 80px;
  background: ${({ theme }) => theme.backgroundColors.default};
  transform: translateX(-50%);
`;
