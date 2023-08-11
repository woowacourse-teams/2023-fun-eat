import { Button, Divider, Heading, Spacing, theme } from '@fun-eat/design-system';
import type { RefObject } from 'react';
import styled from 'styled-components';

import RebuyCheckbox from '../RebuyCheckbox/RebuyCheckbox';
import ReviewImageUploader from '../ReviewImageUploader/ReviewImageUploader';
import ReviewTagList from '../ReviewTagList/ReviewTagList';
import ReviewTextarea from '../ReviewTextarea/ReviewTextarea';
import StarRate from '../StarRate/StarRate';

import { SvgIcon } from '@/components/Common';
import { ProductOverviewItem } from '@/components/Product';
import { useScroll } from '@/hooks/common';
import { useReviewFormActionContext, useReviewFormValueContext } from '@/hooks/context';
import { useProductDetailQuery } from '@/hooks/queries/product';
import { useReviewRegisterFormMutation } from '@/hooks/queries/review';
import { useReviewImageUploader, useFormData } from '@/hooks/review';

const MIN_RATING_SCORE = 0;
const MIN_SELECTED_TAGS_COUNT = 1;
const MIN_CONTENT_LENGTH = 0;

interface ReviewRegisterFormProps {
  productId: number;
  targetRef: RefObject<HTMLElement>;
  closeReviewDialog: () => void;
}

const ReviewRegisterForm = ({ productId, targetRef, closeReviewDialog }: ReviewRegisterFormProps) => {
  const { reviewPreviewImage, setReviewPreviewImage, reviewImageFile, uploadReviewImage, deleteReviewImage } =
    useReviewImageUploader();
  const reviewFormValue = useReviewFormValueContext();
  const { resetReviewFormValue } = useReviewFormActionContext();

  const { data: productDetail } = useProductDetailQuery(productId);
  const { mutate } = useReviewRegisterFormMutation(productId);
  const { scrollToPosition } = useScroll();

  // TODO: 태그 아이디 개수 조건 수정
  const isValid =
    reviewFormValue.rating > MIN_RATING_SCORE &&
    reviewFormValue.tagIds.length === MIN_SELECTED_TAGS_COUNT &&
    reviewFormValue.content.length > MIN_CONTENT_LENGTH;

  const formData = useFormData({
    imageKey: 'image',
    imageFile: reviewImageFile,
    formContentKey: 'reviewRequest',
    formContent: reviewFormValue,
  });

  const handleSubmit: React.FormEventHandler<HTMLFormElement> = async (event) => {
    event.preventDefault();

    await mutate(formData);

    setReviewPreviewImage('');
    resetReviewFormValue();

    closeReviewDialog();
    scrollToPosition(targetRef);
  };

  return (
    <ReviewRegisterFormContainer>
      <ReviewHeading tabIndex={0}>리뷰 작성</ReviewHeading>
      <CloseButton variant="transparent" onClick={closeReviewDialog} aria-label="닫기">
        <SvgIcon variant="close" color={theme.colors.black} width={20} height={20} />
      </CloseButton>
      <Divider />
      <ProductOverviewItemWrapper>
        <ProductOverviewItem name={productDetail?.name} image={productDetail?.image} />
      </ProductOverviewItemWrapper>
      <Divider customHeight="4px" variant="disabled" />
      <RegisterForm onSubmit={handleSubmit}>
        <ReviewImageUploader
          reviewPreviewImage={reviewPreviewImage}
          uploadReviewImage={uploadReviewImage}
          deleteReviewImage={deleteReviewImage}
        />
        <Spacing size={60} />
        <StarRate rating={reviewFormValue.rating} />
        <Spacing size={60} />
        <ReviewTagList selectedTags={reviewFormValue.tagIds} />
        <Spacing size={60} />
        <ReviewTextarea content={reviewFormValue.content} />
        <Spacing size={80} />
        <RebuyCheckbox />
        <Spacing size={16} />
        <FormButton type="submit" customWidth="100%" customHeight="60px" size="xl" weight="bold" disabled={!isValid}>
          {isValid ? '리뷰 등록하기' : '꼭 입력해야 하는 항목이 있어요'}
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
