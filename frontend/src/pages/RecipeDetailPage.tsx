import { Heading, Spacing, Text, theme } from '@fun-eat/design-system';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

import { SectionTitle, SvgIcon } from '@/components/Common';
import { useRecipeDetailQuery } from '@/hooks/queries/recipe';
import { getFormattedDate } from '@/utils/date';

const RecipeDetailPage = () => {
  const { recipeId } = useParams();

  const { data: recipeDetail } = useRecipeDetailQuery(Number(recipeId));

  if (!recipeDetail) return;

  const { images, title, content, author, products, totalPrice, favoriteCount, favorite, createdAt } = recipeDetail;

  return (
    <>
      <SectionTitle name={title} />
      <Spacing size={24} />
      <RecipeImageContainer>
        {images.map((image) => (
          <li key={image}>
            <RecipeImage src={image} alt={`${image}`} width={312} height={210} />
          </li>
        ))}
      </RecipeImageContainer>
      <Spacing size={24} />
      <AuthorFavoriteWrapper>
        <AuthorWrapper>
          <AuthorProfileImage src={author.profileImage} alt={`${author.nickname}님의 프로필`} width={45} height={45} />
          <div>
            <Text color={theme.textColors.info}>{author.nickname} 님</Text>
            <Text color={theme.textColors.info}> {getFormattedDate(createdAt)}</Text>
          </div>
        </AuthorWrapper>
        <FavoriteWrapper>
          <SvgIcon
            variant={favorite ? 'favoriteFilled' : 'favorite'}
            color={favorite ? 'red' : theme.colors.gray4}
            width={18}
          />
          <Text weight="bold" size="lg">
            {favoriteCount}
          </Text>
        </FavoriteWrapper>
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
  width: 312px;
  height: 208px;
  object-fit: cover;
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

const FavoriteWrapper = styled.div`
  display: flex;
  gap: 8px;
  align-items: center;
`;

const RecipeUsedProductsWrapper = styled.div`
  padding: 20px;
  border-radius: 8px;
  background: ${({ theme }) => theme.backgroundColors.light};
`;

const RecipeContent = styled(Text)`
  white-space: break-spaces;
`;
