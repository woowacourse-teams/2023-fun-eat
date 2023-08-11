import { Button, Divider, Heading, Spacing, Text, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import RebuyCheckbox from '../RebuyCheckbox/RebuyCheckbox';
import ReviewTagList from '../ReviewTagList/ReviewTagList';
import ReviewTextarea from '../ReviewTextarea/ReviewTextarea';
import StarRate from '../StarRate/StarRate';

import { ImageUploader, SvgIcon } from '@/components/Common';
import { ProductOverviewItem } from '@/components/Product';
import { useReviewFormActionContext, useReviewFormValueContext } from '@/hooks/context';
import { useReviewRegisterFormMutation, useReviewImageUploader, useFormData } from '@/hooks/review';
import type { ProductDetail } from '@/types/product';

const MIN_RATING_SCORE = 0;
const MIN_SELECTED_TAGS_COUNT = 1;
const MIN_CONTENT_LENGTH = 0;

interface ReviewRegisterFormProps {
  product: ProductDetail;
  closeReviewDialog: () => void;
}

const ReviewRegisterForm = ({ product, closeReviewDialog }: ReviewRegisterFormProps) => {
  const { reviewPreviewImage, setReviewPreviewImage, reviewImageFile, uploadReviewImage, deleteReviewImage } =
    useReviewImageUploader();
  const reviewFormValue = useReviewFormValueContext();
  const { resetReviewFormValue } = useReviewFormActionContext();

  const { mutate } = useReviewRegisterFormMutation(product.id);

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
  };

  return (
    <ReviewRegisterFormContainer>
      <ReviewHeading tabIndex={0}>리뷰 작성</ReviewHeading>
      <CloseButton variant="transparent" onClick={closeReviewDialog} aria-label="닫기">
        <SvgIcon variant="close" color={theme.colors.black} width={20} height={20} />
      </CloseButton>
      <Divider />
      <ProductOverviewItemWrapper>
        <ProductOverviewItem name={product.name} image={product.image} />
      </ProductOverviewItemWrapper>
      <Divider customHeight="4px" variant="disabled" />
      <RegisterForm onSubmit={handleSubmit}>
        <ReviewImageUploaderContainer>
          <Heading as="h2" size="xl" tabIndex={0}>
            구매한 상품 사진이 있다면 올려주세요.
          </Heading>
          <Spacing size={2} />
          <Text color={theme.textColors.disabled} tabIndex={0}>
            (사진은 5MB 이하, 1장까지 업로드 할 수 있어요.)
          </Text>
          <Spacing size={20} />
          <ImageUploader
            previewImage={reviewPreviewImage}
            uploadImage={uploadReviewImage}
            deleteImage={deleteReviewImage}
          />
        </ReviewImageUploaderContainer>
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

const ReviewImageUploaderContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const FormButton = styled(Button)`
  background: ${({ theme, disabled }) => (disabled ? theme.colors.gray3 : theme.colors.primary)};
  color: ${({ theme, disabled }) => (disabled ? theme.colors.white : theme.colors.black)};
  cursor: ${({ disabled }) => (disabled ? 'not-allowed' : 'pointer')};
`;
