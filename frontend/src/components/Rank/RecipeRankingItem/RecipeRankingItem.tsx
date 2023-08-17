import { Spacing, Text, useTheme } from '@fun-eat/design-system';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import type { RecipeRanking } from '@/types/ranking';

interface RecipeRankingItemProps {
  rank: number;
  recipe: RecipeRanking;
}

const srcPath = process.env.NODE_ENV === 'development' ? '' : '/images/';

const RecipeRankingItem = ({ rank, recipe }: RecipeRankingItemProps) => {
  const theme = useTheme();
  const { image, title, author, favoriteCount } = recipe;

  return (
    <>
      <RecipeRankingItemContainer>
        <Spacing direction="horizontal" size={12} />
        <Text weight="bold">{rank}</Text>
        <Spacing direction="horizontal" size={12} />
        <RecipeImage src={srcPath + image} alt={`${rank}위 꿀조합`} width={60} height={60} />
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
        <Spacing direction="horizontal" size={56} />
        <AuthorWrapper>
          <AuthorImage src={author.profileImage} alt={`${author.nickname} 님의 프로필`} width={40} height={40} />
          <Text size="sm" color={theme.textColors.sub}>
            {author.nickname} 님
          </Text>
        </AuthorWrapper>
      </RecipeRankingItemContainer>
    </>
  );
};

export default RecipeRankingItem;

const RecipeRankingItemContainer = styled.div`
  display: flex;
  align-items: center;
  height: 72px;
  margin: 12px 0;
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
