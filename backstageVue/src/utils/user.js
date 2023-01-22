import { loginreq} from './axiosFun';
export const login = (params) => { return loginreq("post", "/api/loginPage/login", params) };
