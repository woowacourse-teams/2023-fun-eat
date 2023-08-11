import { Button, Divider, Heading, Spacing, Text, useTheme } from '@fun-eat/design-system';
import type { ChangeEventHandler } from 'react';
import { useState } from 'react';
import styled from 'styled-components';

import RecipeDetailTextarea from '../RecipeDetailTextarea/RecipeDetailTextarea';
import RecipeNameInput from '../RecipeNameInput/RecipeNameInput';
import RecipeUsedProducts from '../RecipeUsedProducts/RecipeUsedProducts';

import { ImageUploader, SvgIcon } from '@/components/Common';
import useImageUploader from '@/hooks/useImageUploader';
import type { RecipeUsedProduct } from '@/types/recipe';

const RecipeRegisterForm = () => {
  const theme = useTheme();

  const { previewImage, imageFile, uploadImage, deleteImage } = useImageUploader();

  const [recipeName, setRecipeName] = useState('');
  const [recipeDetail, setRecipeDetail] = useState('');
  const [usedProducts, setUsedProducts] = useState<RecipeUsedProduct[]>([]);

  const handleRecipeName: ChangeEventHandler<HTMLInputElement> = (e) => {
    setRecipeName(e.currentTarget.value);
  };

  const handleRecipeDetail: ChangeEventHandler<HTMLTextAreaElement> = (e) => {
    setRecipeDetail(e.currentTarget.value);
  };

  const removeUsedProducts = (id: number) => {
    setUsedProducts((prev) => prev.filter((usedProduct) => usedProduct.id !== id));
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
        <RecipeNameInput recipeName={recipeName} handleRecipeName={handleRecipeName} />
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
        <RecipeDetailTextarea recipeDetail={recipeDetail} handleRecipeDetail={handleRecipeDetail} />
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
