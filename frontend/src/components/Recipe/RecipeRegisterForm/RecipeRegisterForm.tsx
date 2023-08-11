import { Button, Divider, Heading, Spacing, Text, useTheme } from '@fun-eat/design-system';
import type { ChangeEventHandler } from 'react';
import { useState } from 'react';
import styled from 'styled-components';

import RecipeDetailTextarea from '../RecipeDetailTextarea/RecipeDetailTextarea';
import RecipeNameInput from '../RecipeNameInput/RecipeNameInput';
import RecipeUsedProducts from '../RecipeUsedProducts/RecipeUsedProducts';

import { ImageUploader, SvgIcon } from '@/components/Common';
import { useFormData, useImageUploader } from '@/hooks/common';
import type { RecipeRequest, RecipeRequestKey, RecipeUsedProduct } from '@/types/recipe';

interface RecipeFormActionParams {
  target: RecipeRequestKey;
  value: string | number;
}
const RecipeRegisterForm = () => {
  const theme = useTheme();

  const { previewImage, imageFile, uploadImage, deleteImage } = useImageUploader();
  const [usedProducts, setUsedProducts] = useState<RecipeUsedProduct[]>([]);
  const [recipeFormValue, setRecipeFormValue] = useState<RecipeRequest>({
    title: '',
    productIds: [],
    content: '',
  });

  const handleRecipeFormValue = ({ target, value }: RecipeFormActionParams) => {
    setRecipeFormValue((prev) => {
      const targetValue = prev[target];
      if (Array.isArray(targetValue)) {
        return { ...prev, [target]: targetValue.filter((id) => id !== value) };
      }
      return { ...prev, [target]: value };
    });
  };

  const formData = useFormData<RecipeRequest>({
    imageKey: 'images',
    imageFile: imageFile === null ? imageFile : [imageFile],
    formContentKey: 'recipeRequest',
    formContent: recipeFormValue,
  });

  const handleRecipeName: ChangeEventHandler<HTMLInputElement> = (e) => {
    handleRecipeFormValue({ target: 'title', value: e.currentTarget.value });
  };

  const handleRecipeDetail: ChangeEventHandler<HTMLTextAreaElement> = (e) => {
    handleRecipeFormValue({ target: 'content', value: e.currentTarget.value });
  };

  const removeUsedProducts = (id: number) => {
    setUsedProducts((prev) => prev.filter((usedProduct) => usedProduct.id !== id));
    handleRecipeFormValue({ target: 'productIds', value: id });
  };

  return (
    <RecipeRegisterFormContainer>
      <RecipeHeading tabIndex={0}>나만의 꿀조합 만들기🍯</RecipeHeading>
      <CloseButton variant="transparent" aria-label="닫기">
        <SvgIcon variant="close" color={theme.colors.black} width={20} height={20} />
      </CloseButton>
      <Divider />
      <Spacing size={36} />
      <form>
        <RecipeNameInput recipeName={recipeFormValue.title} handleRecipeName={handleRecipeName} />
        <Spacing size={36} />
        <RecipeUsedProducts usedProducts={usedProducts} removeUsedProducts={removeUsedProducts} />
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
        <Spacing size={36} />
        <RecipeDetailTextarea recipeDetail={recipeFormValue.content} handleRecipeDetail={handleRecipeDetail} />
        <Spacing size={36} />
        <FormButton customWidth="100%" customHeight="60px" size="xl" weight="bold">
          레시피 등록하기
        </FormButton>
      </form>
    </RecipeRegisterFormContainer>
  );
};

export default RecipeRegisterForm;

const RecipeRegisterFormContainer = styled.div`
  position: relative;
  height: 100%;
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
