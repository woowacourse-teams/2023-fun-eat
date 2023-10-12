import CommentItem from '../CommentItem/CommentItem';

import type { Comment } from '@/types/recipe';

interface CommentListProps {
  comments: Comment[];
}

const CommentList = ({ comments }: CommentListProps) => {
  return (
    <>
      {comments.map((comment) => (
        <CommentItem key={comment.id} recipeComment={comment} />
      ))}
    </>
  );
};

export default CommentList;
