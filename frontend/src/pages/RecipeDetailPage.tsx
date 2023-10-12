import { Divider, Heading, Spacing, Text, theme } from '@fun-eat/design-system';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

import RecipePreviewImage from '@/assets/plate.svg';
import { SectionTitle } from '@/components/Common';
import { CommentInput, CommentItem, RecipeFavorite } from '@/components/Recipe';
import { useRecipeCommentQuery, useRecipeDetailQuery } from '@/hooks/queries/recipe';
import { getFormattedDate } from '@/utils/date';

export const RecipeDetailPage = () => {
  const { recipeId } = useParams();

  const { data: recipeDetail } = useRecipeDetailQuery(Number(recipeId));
  const { data: recipeComments } = useRecipeCommentQuery(Number(recipeId));
  const { id, images, title, content, author, products, totalPrice, favoriteCount, favorite, createdAt } = recipeDetail;

  return (
    <RecipeDetailPageContainer>
      <SectionTitle name={title} />
      <Spacing size={24} />
      {images.length > 0 ? (
        <RecipeImageContainer>
          {images.map((image, index) => (
            <li key={image}>
              <RecipeImage src={image} alt={`${title} 꿀조합 사진 ${index}`} width={312} height={210} />
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
          <AuthorProfileImage src={author.profileImage} alt={`${author.nickname}님의 프로필`} width={45} height={45} />
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
      <Spacing size={16} />
      <Divider variant="disabled" />
      <Spacing size={16} />
      <Heading as="h3" size="lg">
        댓글 ({recipeComments.length}개)
      </Heading>
      <Spacing size={12} />
      {recipeComments.map((comment) => (
        <CommentItem key={comment.id} comment={comment} />
      ))}
      <CommentInput recipeId={Number(recipeId)} />
      <Spacing size={12} />
    </RecipeDetailPageContainer>
  );
};

const RecipeDetailPageContainer = styled.div`
  padding: 20px 20px 0;
`;

const RecipeImageContainer = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 20px;
  align-items: center;
`;

const RecipeImage = styled.img`
  border-radius: 10px;
  object-fit: cover;
`;

const RecipePreviewImageWrapper = styled.div`
  display: flex;
  justify-content: center;
`;

const AuthorFavoriteWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
`;

const AuthorWrapper = styled.div`
  display: flex;
  gap: 12px;
  align-items: center;
`;

const AuthorProfileImage = styled.img`
  border: 1px solid ${({ theme }) => theme.colors.primary};
  border-radius: 50%;
`;

const RecipeUsedProductsWrapper = styled.div`
  padding: 20px;
  border-radius: 8px;
  background: ${({ theme }) => theme.backgroundColors.light};
`;

const RecipeContent = styled(Text)`
  white-space: break-spaces;
`;
