'use strict';

angular.module('consultorioOnlineUiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pathologicBkg', {
                parent: 'entity',
                url: '/pathologicBkgs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.pathologicBkg.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pathologicBkg/pathologicBkgs.html',
                        controller: 'PathologicBkgController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pathologicBkg');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pathologicBkg.detail', {
                parent: 'entity',
                url: '/pathologicBkg/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.pathologicBkg.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pathologicBkg/pathologicBkg-detail.html',
                        controller: 'PathologicBkgDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pathologicBkg');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PathologicBkg', function($stateParams, PathologicBkg) {
                        return PathologicBkg.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pathologicBkg.new', {
                parent: 'pathologicBkg',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/pathologicBkg/pathologicBkg-dialog.html',
                        controller: 'PathologicBkgDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    observations: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('pathologicBkg', null, { reload: true });
                    }, function() {
                        $state.go('pathologicBkg');
                    })
                }]
            })
            .state('pathologicBkg.edit', {
                parent: 'pathologicBkg',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/pathologicBkg/pathologicBkg-dialog.html',
                        controller: 'PathologicBkgDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PathologicBkg', function(PathologicBkg) {
                                return PathologicBkg.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pathologicBkg', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('pathologicBkg.delete', {
                parent: 'pathologicBkg',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/pathologicBkg/pathologicBkg-delete-dialog.html',
                        controller: 'PathologicBkgDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PathologicBkg', function(PathologicBkg) {
                                return PathologicBkg.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pathologicBkg', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
