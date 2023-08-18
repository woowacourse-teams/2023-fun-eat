import { Heading, Spacing, Text, theme } from '@fun-eat/design-system';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

import RecipePreviewImage from '@/assets/plate.svg';
import { SectionTitle } from '@/components/Common';
import { RecipeFavorite } from '@/components/Recipe';
import { IMAGE_SRC_PATH } from '@/constants/path';
import { useRecipeDetailQuery } from '@/hooks/queries/recipe';
import { getFormattedDate } from '@/utils/date';
import { isChangedImage } from '@/utils/image';

const RecipeDetailPage = () => {
  const { recipeId } = useParams();

  const { data: recipeDetail } = useRecipeDetailQuery(Number(recipeId));
  const { id, images, title, content, author, products, totalPrice, favoriteCount, favorite, createdAt } = recipeDetail;

  return (
    <>
      <SectionTitle name={title} />
      <Spacing size={24} />
      {images.length > 0 ? (
        <RecipeImageContainer>
          {images.map((image, index) => (
            <li key={image}>
              <RecipeImage
                src={IMAGE_SRC_PATH + image}
                alt={`${title} 꿀조합 사진 ${index}`}
                width={312}
                height={210}
              />
            </li>
          ))}
        </RecipeImageContainer>
      ) : (
        <RecipePreviewImageWrapper>
          <RecipePreviewImage width={210} height={210} />
        </RecipePreviewImageWrapper>
      )}
      <Spacing size={24} />
      <AuthorFavoriteWrapper>
        <AuthorWrapper>
          <AuthorProfileImage
            src={isChangedImage(author.profileImage) ? IMAGE_SRC_PATH + author.profileImage : author.profileImage}
            alt={`${author.nickname}님의 프로필`}
            width={45}
            height={45}
          />
          <div>
            <Text color={theme.textColors.info}>{author.nickname} 님</Text>
            <Text color={theme.textColors.info}> {getFormattedDate(createdAt)}</Text>
          </div>
        </AuthorWrapper>
        <RecipeFavorite recipeId={id} favorite={favorite} favoriteCount={favoriteCount} />
      </AuthorFavoriteWrapper>
      <Spacing size={24} />
      <RecipeUsedProductsWrapper>
        <Heading as="h3" size="lg">
          🎁 어떤 상품을 사용했나요?
        </Heading>
        <Spacing size={12} />
        <ul>
          {products.map(({ id, name, price }) => (
            <li key={id}>
              <Text color={theme.textColors.info} lineHeight="lg">
                {name} {price.toLocaleString('ko-KR')} 원
              </Text>
            </li>
          ))}
        </ul>
        <Text weight="bold" size="lg" align="right">
          총 {totalPrice.toLocaleString('ko-KR')}원
        </Text>
      </RecipeUsedProductsWrapper>
      <Spacing size={24} />
      <RecipeContent size="lg" lineHeight="lg">
        {content}
      </RecipeContent>
    </>
  );
};

export default RecipeDetailPage;

const RecipeImageContainer = styled.ul`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
`;

const RecipeImage = styled.img`
  object-fit: cover;
  border-radius: 10px;
`;

const RecipePreviewImageWrapper = styled.div`
  display: flex;
  justify-content: center;
`;

const AuthorFavoriteWrapper = styled.div`
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
`;

const AuthorWrapper = styled.div`
  display: flex;
  gap: 12px;
  align-items: center;
`;

const AuthorProfileImage = styled.img`
  border-radius: 50%;
  border: 1px solid ${({ theme }) => theme.colors.primary};
`;

const RecipeUsedProductsWrapper = styled.div`
  padding: 20px;
  border-radius: 8px;
  background: ${({ theme }) => theme.backgroundColors.light};
`;

const RecipeContent = styled(Text)`
  white-space: break-spaces;
`;
