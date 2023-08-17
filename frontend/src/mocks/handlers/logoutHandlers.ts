import { rest } from 'msw';

export const logoutHandlers = [
  rest.post('/api/logout', (req, res, ctx) => {
    return res(
      ctx.status(302),
      ctx.cookie('mockSessionId', '', { path: '/', expires: new Date(0) }),
      ctx.set('Location', '/')
    );
  }),
];
