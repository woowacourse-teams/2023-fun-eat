import { Button, Checkbox, Divider, Heading, Spacing, theme } from '@fun-eat/design-system';
import { useEffect, useState } from 'react';
import styled from 'styled-components';

import ReviewImageUploader from '../ReviewImageUploader/ReviewImageUploader';
import ReviewTagList from '../ReviewTagList/ReviewTagList';
import ReviewTextarea from '../ReviewTextarea/ReviewTextarea';
import StarRate from '../StarRate/StarRate';

import { productApi } from '@/apis';
import { SvgIcon } from '@/components/Common';
import { ProductOverviewItem } from '@/components/Product';
import { MIN_DISPLAYED_TAGS_LENGTH, REVIEW_SORT_OPTIONS } from '@/constants';
import { useProductReviewContext } from '@/hooks/context';
import { useReviewTextarea, useSelectedTags } from '@/hooks/review';
import useReviewImageUploader from '@/hooks/review/useReviewImageUploader';
import useReviewRegisterForm from '@/hooks/review/useReviewRegisterForm';
import useStarRating from '@/hooks/useStarRate';
import type { ProductDetail } from '@/types/product';

const MIN_RATING_SCORE = 0;
const MIN_SELECTED_TAGS_COUNT = 1;
const MIN_CONTENT_LENGTH = 0;

interface ReviewRegisterFormProps {
  product: ProductDetail;
  close: () => void;
}

const ReviewRegisterForm = ({ product, close }: ReviewRegisterFormProps) => {
  const { reviewImage, uploadReviewImage, deleteReviewImage, reviewImageFile, uploadImageFile } =
    useReviewImageUploader();
  const { rating, handleRating } = useStarRating();
  const { selectedTags, toggleTagSelection } = useSelectedTags(MIN_DISPLAYED_TAGS_LENGTH);
  const { content, handleReviewInput } = useReviewTextarea();
  const [rebuy, setRebuy] = useState(false);
  const [submitEnabled, setSubmitEnabled] = useState(false);
  const { setProductReviews } = useProductReviewContext();

  const { request } = useReviewRegisterForm(product.id);

  const handleRebuy = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRebuy(event.target.checked);
  };

  const handleSubmit: React.FormEventHandler<HTMLFormElement> = async (event) => {
    event.preventDefault();

    const formData = new FormData();

    if (reviewImageFile) {
      formData.append('image', reviewImageFile, reviewImageFile.name);
    }

    const reviewRequest = {
      rating,
      tagIds: selectedTags,
      content,
      rebuy,
    };

    const jsonString = JSON.stringify(reviewRequest);

    const jsonBlob = new Blob([jsonString], { type: 'application/json' });

    formData.append('reviewRequest', jsonBlob);

    const url = `https://funeat.site/api/products/${product.id}/reviews`;

    try {
      const response = await fetch(url, {
        method: 'POST',
        body: formData,
        credentials: 'include',
      });

      if (!response.ok) {
        throw new Error(`에러 발생 상태코드:${response.status}`);
      } else {
        console.log(response, '리뷰가 성공적으로 등록되었습니다.');

        const reviewResponse = await productApi.get({
          params: `/${product.id}/reviews`,
          queries: `?sort=${REVIEW_SORT_OPTIONS[0].value}&page=0`,
          credentials: true,
        });
        const { reviews } = await reviewResponse.json();
        setProductReviews(reviews);

        close();
      }
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    const isValid =
      rating > MIN_RATING_SCORE &&
      selectedTags.length === MIN_SELECTED_TAGS_COUNT &&
      content.length > MIN_CONTENT_LENGTH;
    setSubmitEnabled(isValid);
  }, [rating, selectedTags, content]);

  return (
    <ReviewRegisterFormContainer>
      <ReviewHeading>리뷰 작성</ReviewHeading>
      <CloseButton variant="transparent" onClick={close} aria-label="닫기">
        <SvgIcon variant="close" color={theme.colors.black} width={20} height={20} />
      </CloseButton>
      <Divider />
      <ProductOverviewItemWrapper>
        <ProductOverviewItem name={product.name} image={product.image} />
      </ProductOverviewItemWrapper>
      <Divider variant="disabled" css="height:4px;" />
      <RegisterForm onSubmit={handleSubmit}>
        <ReviewImageUploader
          reviewImage={reviewImage}
          uploadReviewImage={uploadReviewImage}
          deleteReviewImage={deleteReviewImage}
          uploadImageFile={uploadImageFile}
        />
        <Spacing size={60} />
        <StarRate rating={rating} handleRating={handleRating} />
        <Spacing size={60} />
        <ReviewTagList selectedTags={selectedTags} toggleTagSelection={toggleTagSelection} />
        <Spacing size={60} />
        <ReviewTextarea content={content} onReviewInput={handleReviewInput} />
        <Spacing size={80} />
        <Checkbox weight="bold" onChange={handleRebuy}>
          재구매할 생각이 있으신가요?
        </Checkbox>
        <Spacing size={16} />
        <FormButton
          type="submit"
          customWidth="100%"
          customHeight="60px"
          size="xl"
          weight="bold"
          disabled={!submitEnabled}
        >
          {submitEnabled ? '리뷰 등록하기' : '꼭 입력해야 하는 항목이 있어요'}
        </FormButton>
      </RegisterForm>
    </ReviewRegisterFormContainer>
  );
};

export default ReviewRegisterForm;

const ReviewRegisterFormContainer = styled.div`
  position: relative;
  height: 100%;
`;

const ReviewHeading = styled(Heading)`
  height: 80px;
  text-align: center;
  font-size: 2.4rem;
  line-height: 80px;
`;

const CloseButton = styled(Button)`
  position: absolute;
  top: 24px;
  right: 32px;
`;

const ProductOverviewItemWrapper = styled.div`
  margin: 15px 0;
`;

const RegisterForm = styled.form`
  padding: 50px 20px;
`;

const FormButton = styled(Button)`
  background: ${({ theme, disabled }) => (disabled ? theme.colors.gray3 : theme.colors.primary)};
  color: ${({ theme, disabled }) => (disabled ? theme.colors.white : theme.colors.black)};
  cursor: ${({ disabled }) => (disabled ? 'not-allowed' : 'pointer')};
`;
