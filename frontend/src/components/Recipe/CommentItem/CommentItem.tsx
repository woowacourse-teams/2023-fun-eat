import { Divider, Spacing, Text, useTheme } from '@fun-eat/design-system';
import styled from 'styled-components';

import type { Comment } from '@/types/recipe';
import { getFormattedDate } from '@/utils/date';

interface CommentItemProps {
  recipeComment: Comment;
}

const CommentItem = ({ recipeComment }: CommentItemProps) => {
  const theme = useTheme();
  const { author, comment, createdAt } = recipeComment;

  return (
    <>
      <AuthorWrapper>
        <AuthorProfileImage src={author.profileImage} alt={`${author.nickname}님의 프로필`} width={32} height={32} />
        <div>
          <Text size="xs" color={theme.textColors.info}>
            {author.nickname} 님
          </Text>
          <Text size="xs" color={theme.textColors.info}>
            {getFormattedDate(createdAt)}
          </Text>
        </div>
      </AuthorWrapper>
      <CommentContent size="sm">{comment}</CommentContent>
      <Divider variant="disabled" />
      <Spacing size={16} />
    </>
  );
};

export default CommentItem;

const AuthorWrapper = styled.div`
  display: flex;
  gap: 12px;
  align-items: center;
`;

const AuthorProfileImage = styled.img`
  border: 1px solid ${({ theme }) => theme.colors.primary};
  border-radius: 50%;
`;

const CommentContent = styled(Text)`
  margin: 16px 0;
`;
