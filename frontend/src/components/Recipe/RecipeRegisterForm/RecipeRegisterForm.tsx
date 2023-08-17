import { Button, Divider, Heading, Spacing, Text, useTheme } from '@fun-eat/design-system';
import type { FormEventHandler } from 'react';
import styled from 'styled-components';

import RecipeDetailTextarea from '../RecipeDetailTextarea/RecipeDetailTextarea';
import RecipeNameInput from '../RecipeNameInput/RecipeNameInput';
import RecipeUsedProducts from '../RecipeUsedProducts/RecipeUsedProducts';

import { ImageUploader, SvgIcon } from '@/components/Common';
import { useFormData, useImageUploader } from '@/hooks/common';
import { useRecipeFormValueContext, useRecipeFormActionContext } from '@/hooks/context';
import { useRecipeRegisterFormMutation } from '@/hooks/queries/recipe';
import type { RecipeRequest } from '@/types/recipe';

interface RecipeRegisterFormProps {
  closeRecipeDialog: () => void;
}

const RecipeRegisterForm = ({ closeRecipeDialog }: RecipeRegisterFormProps) => {
  const theme = useTheme();

  const { previewImage, imageFile, uploadImage, deleteImage } = useImageUploader();

  const recipeFormValue = useRecipeFormValueContext();
  const { resetRecipeFormValue } = useRecipeFormActionContext();

  const { mutate } = useRecipeRegisterFormMutation();

  const isValid =
    recipeFormValue.title.length > 0 && recipeFormValue.content.length > 0 && recipeFormValue.productIds.length > 0;

  const formData = useFormData<RecipeRequest>({
    imageKey: 'images',
    imageFile: imageFile === null ? imageFile : [imageFile],
    formContentKey: 'recipeRequest',
    formContent: recipeFormValue,
  });

  const handleRecipeFormSubmit: FormEventHandler<HTMLFormElement> = async (event) => {
    event.preventDefault();

    mutate(formData, {
      onSuccess: () => {
        deleteImage();
        resetRecipeFormValue();
        closeRecipeDialog();
      },
      onError: (error) => {
        if (error instanceof Error) {
          alert(error.message);
          return;
        }

        alert('꿀조합 등록을 다시 시도해주세요');
      },
    });
  };

  return (
    <RecipeRegisterFormContainer>
      <RecipeHeading tabIndex={0}>나만의 꿀조합 만들기🍯</RecipeHeading>
      <CloseButton variant="transparent" aria-label="닫기" onClick={closeRecipeDialog}>
        <SvgIcon variant="close" color={theme.colors.black} width={20} height={20} />
      </CloseButton>
      <Divider />
      <Spacing size={36} />
      <form onSubmit={handleRecipeFormSubmit}>
        <RecipeNameInput recipeName={recipeFormValue.title} />
        <Spacing size={40} />
        <RecipeUsedProducts />
        <Spacing size={36} />
        <Heading as="h2" size="xl" tabIndex={0}>
          완성된 꿀조합 사진을 올려주세요.
        </Heading>
        <Spacing size={2} />
        <Text color={theme.textColors.disabled} tabIndex={0}>
          (사진은 5MB 이하, 1장까지 업로드 할 수 있어요.)
        </Text>
        <Spacing size={12} />
        <ImageUploader previewImage={previewImage} uploadImage={uploadImage} deleteImage={deleteImage} />
        <Spacing size={40} />
        <RecipeDetailTextarea recipeDetail={recipeFormValue.content} />
        <Spacing size={40} />
        <Text size="sm" color={theme.textColors.disabled}>
          [작성시 유의사항] 신뢰성 확보에 저해되는 게시물은 삭제하거나 보이지 않게 할 수 있습니다.
        </Text>
        <Spacing size={10} />
        <FormButton customWidth="100%" customHeight="60px" size="xl" weight="bold" disabled={!isValid}>
          레시피 등록하기
        </FormButton>
        <Spacing size={50} />
      </form>
    </RecipeRegisterFormContainer>
  );
};

export default RecipeRegisterForm;

const RecipeRegisterFormContainer = styled.div`
  position: relative;
  height: 100%;
  padding: 0 24px;
`;

const RecipeHeading = styled(Heading)`
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

const FormButton = styled(Button)`
  background: ${({ theme, disabled }) => (disabled ? theme.colors.gray3 : theme.colors.primary)};
  color: ${({ theme, disabled }) => (disabled ? theme.colors.white : theme.colors.black)};
  cursor: ${({ disabled }) => (disabled ? 'not-allowed' : 'pointer')};
`;
