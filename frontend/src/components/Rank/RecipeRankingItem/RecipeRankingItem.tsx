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
          <Text weight="bold">{rank}</Text>
          <Spacing direction="horizontal" size={12} />
          {image !== null ? (
            <RecipeImage src={image} alt={`${rank}위 꿀조합`} width={60} height={60} />
          ) : (
            <RecipePreviewImage width={60} height={60} />
          )}
          <Spacing direction="horizontal" size={12} />
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
  max-width: 560px;
  width: calc(100% - 50px);
  height: 72px;
  margin: 12px 0;
  padding: 0 24px;
`;

const RecipeRankingWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
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
  align-items: center;
  gap: 4px;
`;

const AuthorWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  height: 100%;
`;

const AuthorImage = styled.img`
  border: 2px solid ${({ theme }) => theme.colors.primary};
  border-radius: 50%;
`;
