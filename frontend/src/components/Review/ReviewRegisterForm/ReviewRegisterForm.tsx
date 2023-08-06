import { Button, Checkbox, Divider, Heading, Spacing, theme } from '@fun-eat/design-system';
import type { ChangeEventHandler } from 'react';
import { useRef, useState } from 'react';
import styled from 'styled-components';

import ReviewImageUploader from '../ReviewImageUploader/ReviewImageUploader';
import ReviewTagList from '../ReviewTagList/ReviewTagList';
import ReviewTextarea from '../ReviewTextarea/ReviewTextarea';
import StarRate from '../StarRate/StarRate';

import { productApi } from '@/apis';
import { SvgIcon } from '@/components/Common';
import { ProductOverviewItem } from '@/components/Product';
import { MIN_DISPLAYED_TAGS_LENGTH, REVIEW_SORT_OPTIONS } from '@/constants';
import { useProductReviewContext, useProductReviewPageContext } from '@/hooks/context';
import {
  useReviewRegisterForm,
  useReviewImageUploader,
  useReviewTextarea,
  useSelectedTags,
  useFormData,
  useStarRate,
} from '@/hooks/review';
import useEnterKeyDown from '@/hooks/useEnterKeyDown';
import type { ProductDetail } from '@/types/product';

const MIN_RATING_SCORE = 0;
const MIN_SELECTED_TAGS_COUNT = 1;
const MIN_CONTENT_LENGTH = 0;

interface ReviewRegisterFormProps {
  product: ProductDetail;
  close: () => void;
}

const ReviewRegisterForm = ({ product, close: closeReviewDialog }: ReviewRegisterFormProps) => {
  const { reviewPreviewImage, setReviewPreviewImage, reviewImageFile, uploadReviewImage, deleteReviewImage } =
    useReviewImageUploader();
  const { rating, setRating, handleRating } = useStarRate();
  const { selectedTags, setSelectedTags, toggleTagSelection } = useSelectedTags(MIN_DISPLAYED_TAGS_LENGTH);
  const { content, setContent, handleReviewInput } = useReviewTextarea();
  const [rebuy, setRebuy] = useState(false);

  const formContent = {
    rating,
    tagIds: selectedTags,
    content,
    rebuy,
  };
  const { formData } = useFormData({
    imageKey: 'image',
    imageFile: reviewImageFile,
    formContentKey: 'reviewRequest',
    formContent,
  });

  const labelRef = useRef<HTMLLabelElement | null>(null);
  const { inputRef, handleKeydown } = useEnterKeyDown();
  // const [submitEnabled, setSubmitEnabled] = useState(false);
  const { setProductReviews } = useProductReviewContext();
  const { resetPage } = useProductReviewPageContext();

  const formRef = useRef<HTMLFormElement>(null);
  const { request } = useReviewRegisterForm(product.id);

  const handleRebuy: ChangeEventHandler<HTMLInputElement> = (event) => {
    setRebuy(event.target.checked);
  };

  const handleSubmit: React.FormEventHandler<HTMLFormElement> = async (event) => {
    event.preventDefault();

    if (
      rating <= MIN_RATING_SCORE ||
      selectedTags.length < MIN_SELECTED_TAGS_COUNT ||
      content.length <= MIN_CONTENT_LENGTH
    ) {
      alert('필수 입력 사항을 작성해주세요.');
      return;
    }

    await request(formData);

    const reviewResponse = await productApi.get({
      params: `/${product.id}/reviews`,
      queries: `?sort=${REVIEW_SORT_OPTIONS[0].value}&page=0`,
      credentials: true,
    });

    const { reviews } = await reviewResponse.json();
    setProductReviews(reviews);
    resetPage();

    setReviewPreviewImage('');
    setRating(0);
    setSelectedTags([]);
    setContent('');
    setRebuy(false);

    closeReviewDialog();
  };

  //useEffect(() => {
  //  const isValid =
  //    rating > MIN_RATING_SCORE &&
  //    selectedTags.length === MIN_SELECTED_TAGS_COUNT &&
  //    content.length > MIN_CONTENT_LENGTH;
  //  setSubmitEnabled(isValid);
  //}, [rating, selectedTags, content]);

  return (
    <ReviewRegisterFormContainer>
      <ReviewHeading tabIndex={0}>리뷰 작성</ReviewHeading>
      <CloseButton variant="transparent" onClick={close} aria-label="닫기">
        <SvgIcon variant="close" color={theme.colors.black} width={20} height={20} />
      </CloseButton>
      <Divider />
      <ProductOverviewItemWrapper>
        <ProductOverviewItem name={product.name} image={product.image} />
      </ProductOverviewItemWrapper>
      <Divider variant="disabled" css="height:4px;" />
      <RegisterForm ref={formRef} onSubmit={handleSubmit}>
        <ReviewImageUploader
          reviewPreviewImage={reviewPreviewImage}
          uploadReviewImage={uploadReviewImage}
          deleteReviewImage={deleteReviewImage}
        />
        <Spacing size={60} />
        <StarRate rating={rating} handleRating={handleRating} />
        <Spacing size={60} />
        <ReviewTagList selectedTags={selectedTags} toggleTagSelection={toggleTagSelection} />
        <Spacing size={60} />
        <ReviewTextarea content={content} onReviewInput={handleReviewInput} />
        <Spacing size={80} />
        <p onKeyDown={handleKeydown}>
          <Checkbox ref={labelRef} inputRef={inputRef} weight="bold" onChange={handleRebuy} tabIndex={0}>
            재구매할 생각이 있으신가요?
          </Checkbox>
        </p>
        <Spacing size={16} />
        <FormButton
          type="submit"
          customWidth="100%"
          customHeight="60px"
          size="xl"
          weight="bold"
          //disabled={!submitEnabled}
        >
          {/*{submitEnabled ? '리뷰 등록하기' : '꼭 입력해야 하는 항목이 있어요'}*/}
          리뷰 등록하기
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
  /*background: ${({ theme, disabled }) => (disabled ? theme.colors.gray3 : theme.colors.primary)};*/
  /*color: ${({ theme, disabled }) => (disabled ? theme.colors.white : theme.colors.black)};*/
  /*cursor: ${({ disabled }) => (disabled ? 'not-allowed' : 'pointer')};*/
`;
