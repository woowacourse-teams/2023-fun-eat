import { Text, useTheme } from '@fun-eat/design-system';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import type { Recipe } from '@/types/recipe';
import { getFormattedDate } from '@/utils/date';

interface RecipeItemProps {
  recipe: Recipe;
}

const RecipeItem = ({ recipe }: RecipeItemProps) => {
  const { image, title, author, createdAt, favoriteCount } = recipe;
  const theme = useTheme();

  return (
    <RecipeItemContainer>
      <ImageWrapper>
        <RecipeImage src={image} />
        <ProfileImage src={author.profileImage} />
      </ImageWrapper>
      <RecipeInfoWrapper>
        <Text color={theme.textColors.sub}>
          {author.nickname} 님 | {getFormattedDate(createdAt)}
        </Text>
        <Text size="xl" weight="bold">
          {title}
        </Text>
        {/*TODO: 임시 데이터, API 연동 후 수정*/}
        <Text color={theme.textColors.info}>#불닭볶음면 #옥수수콘 #치즈...</Text>
        <FavoriteWrapper>
          <SvgIcon variant="favoriteFilled" width={16} height={16} />
          <Text as="span" weight="bold">
            {favoriteCount}
          </Text>
        </FavoriteWrapper>
      </RecipeInfoWrapper>
    </RecipeItemContainer>
  );
};

export default RecipeItem;

const RecipeItemContainer = styled.div`
  height: 280px;
`;

const ImageWrapper = styled.div`
  position: relative;
  width: 100%;
  height: 160px;
`;

const RecipeImage = styled.img`
  width: 100%;
  height: 100%;
  border-radius: 8px;
  object-fit: cover;
`;

const ProfileImage = styled.img`
  position: absolute;
  bottom: -20px;
  right: 16px;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background-color: ${({ theme }) => theme.backgroundColors.default};
  border: 2px solid ${({ theme }) => theme.colors.primary};
`;

const RecipeInfoWrapper = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100px;
  margin-top: 20px;
`;

const FavoriteWrapper = styled.div`
  position: absolute;
  top: 50%;
  right: 0;
  bottom: 50%;
  display: flex;
  align-items: center;
  gap: 4px;
  transform: translateY(-50%);
`;
