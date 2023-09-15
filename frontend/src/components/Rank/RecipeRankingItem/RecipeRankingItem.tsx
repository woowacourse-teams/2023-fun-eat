import { Spacing, Text, useTheme } from '@fun-eat/design-system';
import styled from 'styled-components';

import RecipePreviewImage from '@/assets/plate.svg';
import { SvgIcon } from '@/components/Common';
import type { RecipeRanking } from '@/types/ranking';

interface RecipeRankingItemProps {
  rank: number;
  recipe: RecipeRanking;
}

const RecipeRankingItem = ({ rank, recipe }: RecipeRankingItemProps) => {
  const theme = useTheme();
  const {
    image,
    title,
    author: { nickname, profileImage },
    favoriteCount,
  } = recipe;

  return (
    <RecipeRankingItemContainer>
      <Spacing direction="horizontal" size={12} />
      <RecipeRankingWrapper>
        <RankingRecipeWrapper>
          <Spacing direction="horizontal" size={12} />
          {image !== null ? (
            <RecipeImage src={image} alt={`${rank}위 꿀조합`} width={60} height={60} />
          ) : (
            <RecipePreviewImage width={60} height={60} />
          )}
          <Spacing direction="horizontal" size={20} />
          <TitleFavoriteWrapper>
            <Text weight="bold">{title}</Text>
            <FavoriteWrapper>
              <SvgIcon variant="favoriteFilled" width={16} height={16} color="red" />
              <Text as="span" size="sm" weight="bold">
                {favoriteCount}
              </Text>
            </FavoriteWrapper>
          </TitleFavoriteWrapper>
        </RankingRecipeWrapper>
        <AuthorWrapper>
          <AuthorImage src={profileImage} alt={`${nickname} 님의 프로필`} width={40} height={40} />
          <Text size="sm" color={theme.textColors.sub}>
            {nickname} 님
          </Text>
        </AuthorWrapper>
      </RecipeRankingWrapper>
    </RecipeRankingItemContainer>
  );
};

export default RecipeRankingItem;

const RecipeRankingItemContainer = styled.div`
  width: calc(100% - 50px);
  max-width: 560px;
  margin: 12px 0;
  padding: 0 5px;
`;

const RecipeRankingWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  width: 95%;
`;

const RankingRecipeWrapper = styled.div`
  display: flex;
  align-items: center;
`;

const RecipeImage = styled.img`
  border-radius: 5px;
`;

const TitleFavoriteWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  height: 100%;
`;

const FavoriteWrapper = styled.div`
  display: flex;
  gap: 4px;
  align-items: center;
`;

const AuthorWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  height: 100%;
`;

const AuthorImage = styled.img`
  border: 2px solid ${({ theme }) => theme.colors.primary};
  border-radius: 50%;
`;
