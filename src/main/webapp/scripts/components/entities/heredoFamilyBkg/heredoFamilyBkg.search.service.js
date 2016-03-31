'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('HeredoFamilyBkgSearch', function ($resource) {
        return $resource('api/_search/heredoFamilyBkgs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
