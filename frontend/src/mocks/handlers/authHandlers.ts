import { rest } from 'msw';

export const authHandlers = [
  rest.get('/api/auth/kakao', (_, res, ctx) => {
    return res(ctx.status(302), ctx.set('Location', 'http://localhost:3000/profile'));
  }),
];
