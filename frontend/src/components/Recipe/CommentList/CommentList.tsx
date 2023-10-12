import { Heading, Spacing } from '@fun-eat/design-system';

import CommentItem from '../CommentItem/CommentItem';

import { useRecipeCommentQuery } from '@/hooks/queries/recipe';

interface CommentListProps {
  recipeId: number;
}

const CommentList = ({ recipeId }: CommentListProps) => {
  const { data: comments } = useRecipeCommentQuery(Number(recipeId));

  return (
    <>
      <Heading as="h3" size="lg">
        댓글 ({comments.length}개)
      </Heading>
      <Spacing size={12} />
      {comments.map((comment) => (
        <CommentItem key={comment.id} recipeComment={comment} />
      ))}
    </>
  );
};

export default CommentList;
