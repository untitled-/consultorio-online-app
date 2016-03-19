'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('NonPathologicBkgSearch', function ($resource) {
        return $resource('api/_search/nonPathologicBkgs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
